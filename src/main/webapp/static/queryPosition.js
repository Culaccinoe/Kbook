function timeFormat(time){
	    var d = new Date(time);
	    var year=d.getFullYear();
	    var day=d.getDate();
	    var month=+d.getMonth()+1;
	    if(parseInt(month)<10){
	    	month="0"+month;
	    }
	    var hour=d.getHours();
	    var minute=d.getMinutes();
	    var second=d.getSeconds();
	    if(parseInt(day)<10){
	    	day="0"+day;
	    }
	    if(parseInt(minute)<10){
	    	minute="0"+minute;
	    }
	    if(parseInt(hour)<10){
	    	hour="0"+hour;
	    }
	   if(parseInt(second)<10){
		    second="0"+second;
	    }
	    var f=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	    return f;
}
function queryPositionList2(start,limit) {
	
	if (start == null || start == ""||start<0) {
		start = 0;
	}
	
	limit = 10;
	var params = {
			"positionInfo":{
			"positionName" : $("#positionName").val(),
			"start" : start,
			"limit" : limit
			
			}
		}
		var positionName = $("#positionName").val();
	    var id=$("#positionCode").val();
	    var status=$("#status").val();
		   $.ajax({
	           type: 'post',
	           url: my_urlBase()+"/kcsp-web/position/queryPositionList.do",
	           dataType:"json",
	           data :{
	        	   "positionInfo":JSON.stringify ({
	        			"positionName" : positionName,
	        			"id":id,
	        			"status":status,
	        			"start" : start,
	        			"limit" : limit
	        			})
	        	   
	           },
	           success: function(data){ 
	        	var totalCount=data.content.result.count;
	        	$('#positionBody').html('');
	   			var dataList = data.content.result.rows;
	   			var toAddString = "";
	   			$.each(dataList, function(i, obj) {
	   		
	   				if(dataList[i].status==0){
	   					dataList[i].status='禁用';
	   				}
	   				if(dataList[i].status==1){
	   					dataList[i].status='正常';
	   				}
	   				if(dataList[i].status==2){
	   					dataList[i].status='删除';
	   				}
	   			   if(!dataList[i].parentName){
	   				dataList[i].parentName='无';
	   			   }
	   				//子集岗位的状态
	   				if(dataList[i].subPositionList){
	   					$.each(dataList[i].subPositionList, function(j, o) {
	   						if(o.status==0){
	   		   					o.status='禁用';
	   		   				}
	   		   				if(o.status==1){
	   		   					o.status='正常';
	   		   				}
	   					})
	   				}
	   			  	var time=dataList[i].createTime;
	   			  	var f=timeFormat(time);
	   			   
	   				toAddString = toAddString + "<tr id='id_"+i+"'><td>" + dataList[i].positionName
	   				        + "</td><td>" +dataList[i].parentName
	   				        + "</td><td>" +dataList[i].companyName
	   						+ "</td><td>" + f
	   						+ "</td><td>" + dataList[i].status + "</td>"
	   						+'<td><a href="#"><span  onclick="updatePosition(\''+dataList[i].id+'\',\''+dataList[i].parentId+'\',\''+dataList[i].positionName+'\',\''+dataList[i].companyId+'\',\''+dataList[i].status+'\',\''+start+'\',\''+limit+'\')"> 更新</span></a>'
	   						+'<a href="#"><span  onclick="deletePosition(\''+dataList[i].id+'\',\''+start+'\',\''+limit+'\')"> 删除</span></a></td>'
	   						+"</tr>";
	   			 
	   			/* if(dataList[i].subPositionList){
	   				 //循环子列表
	   				$.each(dataList[i].subPositionList, function(j, o) {
	   					var subtime=timeFormat(o.createTime);
	   					toAddString = toAddString + "<tr id='id_"+i+"'><td>" + o.positionName
   				        + "</td><td>" +o.parentName
   				        + "</td><td>" +o.companyName
   						+ "</td><td>" +subtime
   						+ "</td><td>" + o.status + "</td>"
   						+'<td><a href="#"><span  onclick="updatePosition(\''+o.id+'\',\''+o.parentId+'\',\''+o.positionName+'\',\''+o.companyId+'\',\''+o.status+'\')"> 更新</span></a>'
   						+'<a href="#"><span  onclick="deletePosition(\''+o.id+'\')"> 删除</span></a></td>'
   						+"</tr>";
	   				})
	   				 
	   				 
	   			 }		*/
	   			});
	   			$('#positionBody').append(toAddString);
	   			createPagination(start, limit, totalCount);
	          } ,
	          error:function(){
	          	
	          	alert("网络错误");
	          	
	          }
	      });
}

function createPagination(start, limit, total) {
	var current = Math.ceil(start / limit) + 1;
	var begin = Math.max(1, Math.ceil(current - limit / 2));
	var end = Math.min(begin + (limit - 1), Math.ceil(total / limit));
	var page = "<nav>";
	page += '<ul class="pagination">';
	page+='<li><a><span aria-hidden="true">共 '+total+' 条</span></a></li>'
	page += '<li><a href="javascript:void(0);" onclick="queryPositionList2(0);" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>';

	for (var i = begin; i <= end; i++) {
		var p = (i - 1) * limit;
		if (i == current) {
			page += '<li class="active"><a href="javascript:void(0);" onclick="queryPositionList2('
					+ p + ');">' + i + '</a></li>';
		} else {
			page += '<li><a href="javascript:void(0);" onclick="queryPositionList2('
					+ p + ');">' + i + '</a></li>';
		}
	}

	page += '<li><a href="javascript:void(0);" onclick="queryPositionList2('
			+ (end - 1)
			* limit
			+ ');" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>';
	page+='</ul>';
	page+='</nav>'
	$('#page').empty();
	$('#page').append(page);
}
function updatePosition(id,parentId,positionName,companyId,status,start,limit){
	    //先生成下拉列表
	 $.ajax({
         type: 'post',
         url: my_urlBase()+"/kcsp-web/company/queryCompany.do",
         dataType:"json",
         data :{
      	   "companyInfo":JSON.stringify ({
      			"status":1
      			})
      	   
         },
         success: function(data){  
        	//先清空,循环遍历生成下拉列表
        	 $("#updateCompanyName").empty();
        	 $("#updateParentName").empty();
        	var dataList = data.content.result.company;
        	var parentList=data.content.result.position;
        	$("#updateParentName").append("<option value='0'>请选择父级部门</option>");
        	$.each(dataList, function(i, obj) {
   				$("#updateCompanyName").append("<option value="+obj.id+">"+obj.name+"</option>");
   						
   			});
        	$.each(parentList, function(j, o) {
   				$("#updateParentName").append("<option value="+o.id+">"+o.positionName+"</option>");
   						
   			});  
        	$('#updateCompanyName').val(companyId);
        	$('#updateCompanyName').attr("disabled",true);
        	$('#updateParentName').val(parentId);
        	//根据companyId查询岗位
        	 $.ajax({
    	         type: 'post',
    	         url: my_urlBase()+"/kcsp-web/position/queryPositionByCompanyId.do",
    	         dataType:"json",
    	         data :{
    	      	   "companyId":companyId
    	      	   
    	         },
    	         success: function(data){  
    	        	if(data.success){
    	        		/* $('#addModal').modal("hide");
    		        	layer.alert("添加成功");
    		   			queryPositionList2(); */
    		   			//alert(1234);
    		   			$("#updateParentName").empty();
    		   			var positionList=data.content.result;	   			
    		        	$("#updateParentName").append("<option value='0'>请选择父级岗位</option>");
    		        	$.each(positionList, function(j, o) {
    		        		
    		   				$("#updateParentName").append("<option value="+o.id+">"+o.positionName+"</option>");
    		   						
    		   			});
    		        	$("#updateParentName").val(parentId);
    	        	}else{
    	        		layer.alert(data.msg);
    	        	}
    	        	 				 
    	        } ,
    	        error:function(){
    	        	
    	        	alert("网络错误");
    	        	
    	        }
    	    });
        	
        } ,
        error:function(){
        	
        	alert("网络错误");
        	
        }
    });
	 
		$('#updateModal').modal("show");
		//alert(companyId);
		$("#updatePositionId").val(id);
		$("#updatePositionId").attr('disabled',true);
		$("#updatePositionName").val(positionName);
		$("#updateStatus").val(status);
		$("#start").val(start);
		$("#limit").val(limit);
		if(status=="正常"){
		$("#updateStatus").val(1);
		}else{
			$("#updateStatus").val(0);
		}
		
}
