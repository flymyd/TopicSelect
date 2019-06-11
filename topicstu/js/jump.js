function indexJump() {
	mui.openWindow({
		url: "main.html",
		id: 1,
		createNew: false, //是否重复创建同样id的webview，默认为false:不重复创建，直接显示
		waiting: {
			autoShow: true, //自动显示等待框，默认为true
			title: '正在加载...', //等待对话框上显示的提示内容
		}
	})
}

function chooseTopicJump() {
	mui.openWindow({
		url: "chooseTopic.html",
		id: 2,
		createNew: true, //是否重复创建同样id的webview，默认为false:不重复创建，直接显示
		waiting: {
			autoShow: true, //自动显示等待框，默认为true
			title: '正在加载...', //等待对话框上显示的提示内容
		}
	})
}

function myTopicJump() {
	mui.openWindow({
		url: "myTopic.html",
		id: 3,
		createNew: true, //是否重复创建同样id的webview，默认为false:不重复创建，直接显示
		waiting: {
			autoShow: true, //自动显示等待框，默认为true
			title: '正在加载...', //等待对话框上显示的提示内容
		}
	})
}

function finalCheckJump() {
	mui.openWindow({
		url: "finalCheck.html",
		id: 4,
		createNew: true, //是否重复创建同样id的webview，默认为false:不重复创建，直接显示
		waiting: {
			autoShow: true, //自动显示等待框，默认为true
			title: '正在加载...', //等待对话框上显示的提示内容
		}
	})
}
