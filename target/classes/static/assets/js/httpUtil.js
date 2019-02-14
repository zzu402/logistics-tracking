function httpRequest(method, url, formData, contentType, dataType, async, success,error) {
    var options = {
        url: url,
        type: method,
        timeout: 100000,
        cache:false,
        beforeSend:function(xhr){

        },
        success:function(data,textStatus,jqXHR){
            if(success && (typeof (success) == "function"))
                success(data,this,textStatus);
        },
        error:function(xhr,textStatus){
            console.log(url+":"+textStatus);
            if(error && (typeof (error) == "function"))
                error(textStatus,this);
        },
        complete:function(){

        }
    };
    if(formData) options.data = formData;
    if(async) options.async = async;
    if(contentType) options.contentType=contentType;
    if(dataType) options.dataType=dataType;

    var xhr = $.ajax(options);
    return xhr;
}
function httpUrlRequest(method, url, formData, contentType, dataType, async, success,error) {
    var tURL = url;
    var paramString="";
    if(formData){
        for (var key in formData) {
            var value = formData[key];
            if ( typeof (value) == "function" ){
                continue;
            } else {
                if(paramString.length>0)
                    paramString = paramString+"&";
                paramString = paramString+key+"="+encodeURIComponent(value);
            }
        }
    }
    if(paramString.length>0){
        if(tURL.indexOf("?")>=0){
            tURL=tURL+"&"+paramString;
        }else{
            tURL=tURL+"?"+paramString;
        }
    }
    return httpRequest(method,tURL,null, contentType, dataType, async, success,error);
}
function httpPost(url, formData, contentType, dataType, async, success,error) {
    return httpRequest("POST",url,formData, contentType, dataType, async, success,error)
}
function httpDelete4Json(url, formData, success, async) {
    return httpUrlRequest("DELETE", url, formData, null, "json", async, success,null);
}
function httpGet4Json(url, formData, success, async) {
    return httpUrlRequest("GET", url, formData, null, "json", async, success,null);
}
function formPost4Json(url, formData, success,async) {
    return httpPost(url,formData,"application/x-www-form-urlencoded","json",async,success);
}
function jsonPost4Json(url, jsonData, success,async) {
    var data = jsonData;
    if(typeof(jsonData) == "object")
        data = JSON.stringify(jsonData);
    return httpPost(url,data,"application/json","json",async,success);
}
