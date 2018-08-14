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
		url : "/orders",
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
		url : "/order/" + id,
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
		url : "/order/" + id,
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
		url : "/order/" + id,
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
		url : "/nextDelivery",
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
		url : "/clearQueue",
		async : true,
		success : function(data) {
			document.getElementById("resultArea").value = JSON.stringify(data);

		},
		error : function(data) {
			alert(JSON.stringify(data) + " fail");
		}
	});
}
