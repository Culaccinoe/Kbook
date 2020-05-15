
+function($){
    jQuery.fn.DropDownList = function(options) {
        
        //设置插件的默认属性
        var defaults ={
            InputName:"Q",
            ButtonText:"示例",
            ReadOnly:true,
            MaxHeight:-1,
            onSelect:$.noop(),
        }
        var options = $.extend(defaults,options);
        return this.each(function(){
            var o=options;
            var Obj=$(this);
   
            var S="<div class='input-group'>";
            S = S + "<input type='text' class='form-control' name='" + o.InputName + "' id='" + o.InputName + "'  />";
            S = S + "<div class='input-group-btn'>";
            S = S + "<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown'>" + o.ButtonText + "<span class='caret'></span></button>";
            S = S + "<ul class='dropdown-menu dropdown-menu-right' role='menu'>";
   
            //可以由Sections参数或Items参数设置下拉条目，Sections的优先级比Items高
            if (o.Sections!== undefined)
            {
                $.each(o.Sections,function(n,value){
                        
                    //从第2节开始，在每节的顶部添加一条分割线
                    if (n>0) { S=S + "<li class='divider'></li>"; }
                    //如果设置了ItemHeader参数，则给该节添加标题文本
                    if (value.ItemHeader!==undefined) { S = S + "<li class='dropdown-header'>" + value.ItemHeader + "</li>"; }
                    CreateItem(value);
                });
            }
            else
            {
                CreateItem(o);
            }
   
            var SelText="";
            var SelData="";
            function CreateItem(Items)
            {
                $.each(Items.Items,function(n,Item){
                    //如果ItemData参数没有定义，则把ItemText参数传给ItemDate
                    if (Item.ItemData===undefined) {Item.ItemData=Item.ItemText;}
                    S=S + "<li><a href='#'  ItemData='"  + Item.ItemData + "' >" + Item.ItemText + "</a></li>";
                    //如果设置了Selected参数，则获取该条目的ItemDada和ItemText。
                    //如果有多个条目设置该参数，则获取的是满足条件最后一个条目
                    if (Item.Selected==true) { SelText=Item.ItemText;SelData=Item.ItemData;}
                });
            }
   
   
            S =S + "</ul></div></div>";
   
            Obj.html(S);
   
            var Input=Obj.find("input");
            //如果有条目设置Selected参数，则调用设置活动条目的函数
            if (SelText!="") { SetData(SelText,SelData); }
   
            //给所有的条目绑定单击事件，单击后调用设置活动条目的函数
            Obj.find("a").bind("click", function(e) {SetData($(this).html(),$(this).attr("ItemData"));});
   
            //如果ReadOnly参数设置为true，则屏蔽掉文本框的相关事件，使得文本框不能编辑。（而又显示为激活状态）
            if (o.ReadOnly==true)
            {
                Input.bind("cut copy paste keydown", function(e) {e.preventDefault();}); 
            }
   
            //设置MaxHeight参数后（大于0），则设置下拉菜单的最大高度，若条目过多，会出现垂直滚动条
            if (o.MaxHeight>0)
            {
                var UL=Obj.find("ul");
                if (UL.height()>o.MaxHeight)
                {UL.css({'height':o.MaxHeight,'overflow':'auto'});}
            }
   
            function SetData(Text,Data)
            {
                Input.val(Text);
                if (o.onSelect) { o.onSelect(o.InputName,Data); }
            }
                  
        });
    }
}(jQuery);

