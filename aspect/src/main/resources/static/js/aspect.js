var resTable = angular.module('resTable', []);

resTable.controller('tableCtrl', function($scope, $http,$timeout) {
	$scope.reload = function() {
		$http({
			method : 'POST',
			url : "http://localhost:8080/orders"
		}).success(function(response) {
			$scope.Orders = response.data;
		});

		$timeout(function() {
			$scope.reload();
		}, 2500)
	};

	$scope.reload();
});

function check() {
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/orders",
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}

function checkid() {
	id = document.getElementById("id").value;
	$.ajax({
		type : "GET",
		data : "",
		url : "http://localhost:8080/order/" + id,
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}

function putOrder() {
	id = document.getElementById("id").value;
	quantity = document.getElementById("quantity").value;
	$.ajax({
		type : "POST",
		data : "quantity=" + quantity,
		url : "http://localhost:8080/order/" + id,
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}

function cancelOrder() {
	id = document.getElementById("id").value;
	$.ajax({
		type : "DELETE",
		data : "",
		url : "http://localhost:8080/order/" + id,
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}

function next() {

	$.ajax({
		type : "POST",
		data : "",
		url : "http://localhost:8080/nextDelivery",
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}

function clearQueue() {

	$.ajax({
		type : "GET",
		data : "",
		url : "http://localhost:8080/clearQueue",
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}
