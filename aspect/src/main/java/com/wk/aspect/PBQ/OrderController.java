package com.wk.aspect.PBQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderController.
 *
 * @author wk
 */
@RestController
// @RequestMapping("/priorityqueue")
public class OrderController {

	/** The queue. */
	static PriorityBlockingQueue<Order> queue = new PriorityBlockingQueue<Order>();
	
	/** The service. */
	ExecutorService service = Executors.newCachedThreadPool();
	
	/** The server start time. */
	static long serverStartTime = System.currentTimeMillis();

	/** The is running. */
	private volatile boolean isRunning = true;

	static {
		queue.offer(new Order(1001, 2, serverStartTime));
		queue.offer(new Order(1003, 2, serverStartTime));
	}

	/**
	 * Put order.
	 *
	 * @param id the id
	 * @param quantity the quantity
	 * @return the json result
	 */
	@RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
	public JsonResult putOrder(@PathVariable("id") Integer id,
			@RequestParam(value = "quantity", required = true) int quantity) {

		//input check
		if (id < 1 || id > 20000) {
			return new JsonResult(ResultCode.DO_NOT_SUPPORT, "user id invalid");
		}

		if (quantity > 25) {
			return new JsonResult(ResultCode.DO_NOT_SUPPORT, "quantity is too much");
		}

		if (contains(id)) {
			return new JsonResult(ResultCode.ALREADY_EXIST, "order already exist");
		}

		Producer producer = new Producer(queue, isRunning, id, quantity, serverStartTime);
		service.execute(producer);

		return new JsonResult(ResultCode.SUCCESS, "put order success", new Order(id, quantity, serverStartTime));

	}

	/**
	 * Check order.
	 *
	 * @param _id the id
	 * @return the json result
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JsonParseException the json parse exception
	 */
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	public @ResponseBody JsonResult checkOrder(@PathVariable("id") Integer _id) throws IOException, JsonParseException {

		//input check
		if (!contains(_id)) {
			return new JsonResult(ResultCode.NOT_EXIST, "order not exist");
		}

		int sum = 0;
		int id = 0;
		int pos = 0;
		Order res = new Order(0, 0, 0l);

		Order[] orderArray = queue.parallelStream().toArray(Order[]::new);
		Arrays.sort(orderArray);

		for (Order anOrder : orderArray) {
			sum += anOrder.getQuantity();
			pos++;
			if (anOrder.getID() == _id) {
				id = anOrder.getID();
				res = anOrder;
				break;
			}
		}
		int timeslot = sum % 25 == 0 ? (sum / 25 - 1) : (sum / 25);
		int time = timeslot * 5;
		String jsonString = "{\"id\":\"" + id + "\",\"time\":\"" + time + "\",\"position\":\"" + pos + "\"}";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonRes = mapper.readTree(jsonString);

		if (res.getID() != 0) {
			return new JsonResult(ResultCode.SUCCESS, "check order success", jsonRes);
		} else {
			return new JsonResult(ResultCode.NOT_EXIST, "order not exist");
		}

	}

	/**
	 * Super check.
	 *
	 * @return the json result
	 * @throws JsonProcessingException the json processing exception
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public JsonResult superCheck()
			throws JsonProcessingException {

		if (queue.size() == 0) {
			return new JsonResult(ResultCode.EMPTY, "no order inside");
		} else {

			int sum = 0;

			Order[] orderArray = queue.parallelStream().toArray(Order[]::new);
			Arrays.sort(orderArray);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
			ArrayNode arrayNode = mapper.createArrayNode();

			for (Order anOrder : orderArray) {
				sum += anOrder.getQuantity();
				ObjectNode objectNode = mapper.createObjectNode();
				objectNode.put("id", anOrder.getID());
				objectNode.put("quantity", anOrder.getQuantity());
				objectNode.put("tiq", anOrder.getTIQ());
				int timeslot = sum % 25 == 0 ? (sum / 25 - 1) : (sum / 25);
				objectNode.put("tiem", timeslot * 5);
				arrayNode.add(objectNode);

			}
			
			return new JsonResult(ResultCode.SUCCESS, "manager check success", arrayNode);
		}
	}

	/**
	 * Next delivery.
	 *
	 * @return the json result
	 */
	@RequestMapping("/nextDelivery")
	public JsonResult nextDelivery() {

		System.out.println("nextDelivery");

		List<Order> res = new ArrayList();
		int sum = 0;

		Order[] orderArray = queue.parallelStream().toArray(Order[]::new);
		Arrays.sort(orderArray);

		for (Order anOrder : orderArray) {
			if (sum + anOrder.getQuantity() > 25) {
				return new JsonResult(ResultCode.SUCCESS, "nextDelivery check success", res);
			}
			res.add(anOrder);
			sum += anOrder.getQuantity();
		}

		if (res.size() > 0) {
			return new JsonResult(ResultCode.SUCCESS, "nextDelivery check success", res);
		} else {
			return new JsonResult(ResultCode.SUCCESS, "no delivery", res);
		}

	}

	/**
	 * Cancel order.
	 *
	 * @param id the id
	 * @return the json result
	 */
	@RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
	public JsonResult cancelOrder(
			@PathVariable("id") Integer id) {
		Consumer consumer = new Consumer(queue, id);
		service.execute(consumer);

		return new JsonResult(ResultCode.SUCCESS, "delete ok");

	}

	/**
	 * Clear queue.
	 *
	 * @return the json result
	 */
	@GetMapping(value = "/clearQueue")
	public JsonResult clearQueue() {
		queue.clear();

		return new JsonResult(ResultCode.SUCCESS, "clear ok");

	}

	/**
	 * Contains.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	private boolean contains(int id) {
		// check
		Iterator<Order> itr = queue.iterator();
		while (itr.hasNext()) {
			Order anOrder = itr.next();
			if (anOrder.getID() == id) {
				return true;
			}
		}

		return false;
	}
}
