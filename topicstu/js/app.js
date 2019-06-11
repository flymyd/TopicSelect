(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.account = loginInfo.account || '';
		loginInfo.password = loginInfo.password || '';
		if (loginInfo.account.length < 1) {
			return callback('账号最短为 1 个字符');
		}
		if (loginInfo.password.length < 3) {
			return callback('密码最短为 3 个字符');
		}
		// 登录动作
		var postdata=JSON.stringify({"username":loginInfo.account,"password":loginInfo.password});
		var purl=conf.ur+"/loginAction";
		$.ajax({
		    url: purl,
		    type: 'post',
		    dataType: 'json',
		    contentType: "application/json",
		    async:false,
			timeout: 10000,
		    data: postdata,
		    success: function(){
				document.getElementById("authCode").value = "1";
		    },
		    error:function(XMLHttpRequest){
				document.getElementById("authCode").value = "2";
		    },
			complete:function(xhr){
				conf.storage.setItem("userToken",xhr.getResponseHeader('x-auth-token'));
				conf.storage.setItem("userPermission",xhr.getResponseHeader('permission'));
			}
		});
		if (document.getElementById("authCode").value === "1") {
			return owner.createState(loginInfo.account, callback);
		} else {
			return callback('用户名或密码错误！');
		}
	};

	owner.createState = function(name, callback) {
		var state = owner.getState();
		state.account = name;
		state.token = conf.storage.getItem("userToken");
		owner.setState(state);
		return callback();
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('$state') || "{}";
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('$state', JSON.stringify(state));
		//var settings = owner.getSettings();
		//settings.gestures = '';
		//owner.setSettings(settings);
	};

	var checkEmail = function(email) {
		email = email || '';
		return (email.length > 3 && email.indexOf('@') > -1);
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	}

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
			var settingsText = localStorage.getItem('$settings') || "{}";
			return JSON.parse(settingsText);
		}
	
}(mui, window.app = {}));