var _sso_sc_url='http://test.site.haiziwang.com/scripts/sso.js';//SSO-URL

function parseTemplete(contentId, tmpId, data) {
	var dfd = $.Deferred();
	var d = {};
	if (data) {
		d = data;
	}

	$("#" + contentId).empty();
	var tpl = $("#" + tmpId).text();
	var tempFn = doT.template(tpl);
	$("#" + contentId).append(tempFn(d));
	dfd.resolve("parse finished!");
	return dfd.promise();
};

function parseTempleteObj(obj, tmpId, data) {
	var dfd = $.Deferred();
	var d = {};
	if (data) {
		d = data;
	}

	obj.empty();
	var tpl = $("#" + tmpId).text();
	var tempFn = doT.template(tpl);
	obj.append(tempFn(d));
	dfd.resolve("parse finished!");
	return dfd.promise();
};

function createForm(data){
	var form='';
	if(data == null || data == ""){
		return form;
	}
	
	var obj = JSON.parse(data);
	for(var p in obj){
		form += '<div class="form-group">'+
			'<label for="'+obj[p]+'" class="col-sm-2 control-label">'+p+'</label>'+
			'<div class="col-sm-9">'+
				'<input type="text" class="form-control" id="'+obj[p]+'">'+
			'</div></div>';
	}
	
	return form;
};

function createEmptyForm(obj){
	var num = Number($(obj).attr("count"));
	
	var form = '<div class="form-group">'+
		 '<div class="col-sm-2">'+
		 	'<input type="text" class="form-control" key id="property'+num+'">'+
		 '</div>'+
		 '<div class="col-sm-9">'+
		 	'<input type="text" class="form-control" property id="content'+num+'">'+
		 '</div>'+
	 '</div>';
	$("#forNewProperty").append(form);
	$(obj).attr("count",num+1);
}

//日期格式化
function add0(m){return m<10?'0'+m:m }
function formatDateUtil(shijianchuo)
{
	//shijianchuo是整数，否则要parseInt转换
	var time = new Date(shijianchuo);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}

function showBulidStatus(status) {
	if (status == 'FAILURE') {
		return '<span class="label label-danger">失败</span>';
	} else if (status == 'UNSTABLE' || status == 'ABORTED') {
		return '<span class="label label-warning">中断</span>';
	} else if (status == 'SUCCESS') {
		return '<span class="label label-success">成功</span>';
	} else if (status == 'BUILDING') {
		return '<span class="label label-primary">构建中</span>';
	}
	return '<span class="label label-default">' + status + '</span>';//REBUILDING, UNKNOWN, NOT_BUILT
}

function isIP(ip)  {  
    var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;  
    if (reSpaceCheck.test(ip)){  
        ip.match(reSpaceCheck);  
        if (RegExp.$1<=255&&RegExp.$1>=0  
          &&RegExp.$2<=255&&RegExp.$2>=0  
          &&RegExp.$3<=255&&RegExp.$3>=0  
          &&RegExp.$4<=255&&RegExp.$4>=0){  
          return true;
        }
    }
}


/*创建分页
 **@ pageIndex  当前页
 **@ pageSize   每页记录数量
 **@ totalCount 总记录条数
 */
function createPagination(pageIndex, pageSize, totalCount) {
    var totalPage = Math.ceil(totalCount / pageSize);//总页数
    var showPageNum = 10;//页面显示页码的数量
    var pageNumString = '';
    var realPageIndex = pageIndex;
    //只显示showPageNum 个页码-------------------------------
    if (totalPage <= showPageNum) {
        pageIndex = 1;
    } else if ((totalPage - pageIndex) < (showPageNum - 1)) {
        pageIndex = (totalPage - showPageNum + 1);
    }
    //只显示showPageNum 个页码-------------------------------
    for (var i = 0; i < totalPage; i++) {
        if ((pageIndex + i) > totalPage) {
            break;
        }
        if (i >= showPageNum) {
            break;
        }

        if ((pageIndex + i) == realPageIndex) {
            pageNumString += '<li class="active"><a href="javascript:void(0);"onclick="queryData('
                + (pageIndex + i)
                + ')">'
                + (pageIndex + i)
                + '</a></li>';
        } else {
            pageNumString += '<li><a href="javascript:void(0);"onclick="queryData('
                + (pageIndex + i)
                + ')">'
                + (pageIndex + i)
                + '</a></li>';
        }
    }
    var pageIndexPre = (realPageIndex - 1) <= 0 ? 1 : (realPageIndex - 1);//前一页
    var pageIndexNext = (realPageIndex + 1) >= totalPage ? totalPage
        : (realPageIndex + 1);//后一页
    var page = "<nav>"
        + '<ul class="pagination"> '
        + '<li><a><span aria-hidden="true">共 '
        + totalCount
        + ' 条</span></a></li>'
        + '<li><a href="javascript:void(0);" onclick="queryData(0);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>'
        + '<li><a href="javascript:void(0);" onclick="queryData('
        + pageIndexPre
        + ');" aria-label="Previous">'
        + '<span aria-hidden="true">&lsaquo;</span></a></li>'
        + pageNumString
        + '<li><a href="javascript:void(0);" onclick="queryData('
        + pageIndexNext
        + ');" aria-label="Next"> '
        + '<span aria-hidden="true">&rsaquo;</span></a></li>'
        + '<li><a href="javascript:void(0);" onclick="queryData('
        + totalPage
        + ');" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>'
        + '</ul>' + '</nav>';
    $('#page').empty();
    $('#page').append(page);
}

