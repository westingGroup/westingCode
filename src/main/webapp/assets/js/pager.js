/*
 desc: ajax in post method and dataType is json
  url:request url
  data:params;
  errFname:when server return error,callback this 
  SuccFname:when server successes,callback;
  asyn:this request is syn or not ,default syn
  
  @bily ban
  
  
 */

var isCanUp = 0;
var isCanDown = 0;

function AjaxpostByJson(url, mydata, succFname, asyn, errFname) {
	var syn;
	var mysFname, myeFname;

	if (typeof (asyn) == 'undefined') {
		syn = true;
	} else {
		syn = asyn;
	}

	if (typeof (succFname) == 'undefined') {
		mysFname = myDefaultSucc;
	} else {
		mysFname = succFname;
	}

	if (typeof (errFname) == 'undefined') {
		myeFname = myDefaultErr;
	} else {
		myeFname = errFname;
	}
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		// contentType : "application/json",
		data : replacePercentToNormal(mydata),
		// data:mydata,
		async : syn,
		timeout : 100000000,
		success : function(data, textStatus, jqXHR) {
			if (data.successFlag) {
				mysFname(data, textStatus, jqXHR);
			} else {
				myeFname(data);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			myeFname(jqXHR, textStatus, errorThrown);
		}
	});
}

function myDefaultSucc(data) {
	showTipsSucc("操作已成功");
}
// 默认错误处理方法
function myDefaultErr(data) {
	if (data.info) {
		showTipsError(data.info);
	} else {
		showTipsError("操作失败");
	}

}

/*
 * repair a jaxa post BUG,when the oldStr have %,then request error,now change %
 * to %25.get it!
 */
function replacePercentToNormal(oldStr) {
	var r, re, r2, r3;
	re = /%/g;
	r = oldStr.replace(re, "%25");
	r2 = r.replace(/\+/g, "%2B")
	r3 = r2.replace(/\&/g, "%26");
	return r2;
}

// 分页统一调用此函数，回调函数callbackfunction，自己重写
// conditions 条件,pageSize 每页显示条数,currPage 当前页,postUrl
// ajax对应url,callbackfunction回调方法
function getPagerInfoByAjax(conditions, pageSize, currPage, postUrl,
		callbackfunction) {
	var question = {
		"condition" : conditions,
		"currPage" : currPage,
		"pageSize" : pageSize
	};
	AjaxpostByJson("/" + URL + postUrl, JSON.stringify(question),
			callbackfunction, false);
}

// demo start
function roleCallback(data) {
	controlPageUpAndDown(data, "currPageNum", "prevLi", "nextLi",
			"totalPageSpan", "currPageSpan");
	filleRoleinfoData(data);

}

function filleRoleinfoData(data) {
	var records = data.content.records;
	$("#usertbody").empty();
	for (var i = 0; i < records.length; i++) {
		var trList = $("<tr></tr>");
		var td1, td2, td3, td4, td5, td6;
		td1 = '<td>' + records[i].roleName + '</td>';
		td2 = '<td>' + records[i].descs + '</td>';
		td3 = '<td>' + records[i].gmtCreate + '</td>';
		td4 = '	<td><a class=" btn green"  href="javascript:onshowAuth(\''
				+ records[i].roleId + '\',\'' + records[i].roleName
				+ '\')">查看</a></td>';
		td5 = '	<td><a class=" btn green"  href="javascript:onedit(\''
				+ records[i].roleId + '\',\'' + records[i].roleName
				+ '\')">编辑</a></td>';
		trList.html(td1 + td2 + td3 + td4 + td5);
		$("#usertbody").append(trList);

	}

	if (records.length == 0) { // 没有记录的时候提示
		var noTr = $("<tr></tr>");
		var td7 = '<td colspan="6" style="text-align:center;">暂无符合条件的记录</td>';
		noTr.html(td7);
		$("#usertbody").append(noTr);
	}

}
// demo end

// 控制翻页，以及第几页，共几页，通用
function controlPageUpAndDown(data, currPageNumId, prevPageId, nextPageId,
		totalPageId, currPageId, listFuction) {

	isCanUp = data.content.isCanUp;
	isCanDown = data.content.isCanDown;

	$("#" + currPageNumId).val(data.content.currentPage);
	if ($("#" + currPageNumId).val() > 1) {
		$("#" + prevPageId).removeClass("disabled");
		if (listFuction) {
			var pageee = parseInt($("#" + currPageNumId).val()) - 1;
			$("#" + prevPageId).find("a").attr("onclick",
					"javascript:" + listFuction + "(" + pageee + ")");
		}

	} else {
		$("#" + prevPageId).addClass("disabled");
		$("#" + prevPageId).find("a").attr("onclick",
				"javascript:;")
	}

	if ($("#" + currPageNumId).val() < data.content.totalPage) {
		$("#" + nextPageId).removeClass("disabled");
		if (listFuction) {
			var pageee = parseInt($("#" + currPageNumId).val()) + 1;

			$("#" + nextPageId).find("a").attr("onclick",
					"javascript:" + listFuction + "(" + pageee + ")");
		}

	} else {
		$("#" + nextPageId).addClass("disabled");
		$("#" + nextPageId).find("a").attr("onclick",
				"javascript:;");
	}

	$("#" + totalPageId).text(
			"共" + data.content.totalPage + "页　　　共" + data.content.totalRecords
					+ "条");
	$("#" + currPageId).text("第" + data.content.currentPage + "页");
}
/*----------------------------------------------------*/
function AjaxpostBycontenttypeJson(url, mydata, succFname, asyn, errFname) {
	var syn;
	var mysFname, myeFname;

	if (typeof (asyn) == 'undefined') {
		syn = true;
	} else {
		syn = asyn;
	}

	if (typeof (succFname) == 'undefined') {
		mysFname = myDefaultSucc;
	} else {
		mysFname = succFname;
	}

	if (typeof (errFname) == 'undefined') {
		myeFname = myDefaultErr;
	} else {
		myeFname = errFname;
	}
	$.ajax({
		type : "POST",
		url : url,
		contentType : "application/json",
		dataType : "json",
		data : mydata,
		async : syn,
		timeout : 100000000,
		success : function(data, textStatus, jqXHR) {
			if (data.successFlag) {
				mysFname(data, textStatus, jqXHR);
			} else {
				myeFname(data);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			myeFname(jqXHR, textStatus, errorThrown);
		}
	});
}
