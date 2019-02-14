function getCacheFromSessionStorage(key) {
    var cacheStr = sessionStorage.getItem(key);
    if (cacheStr)
        return JSON.parse(cacheStr);
    return null;
}

function setCache2SessionStorage(key, value) {
    if (value)
        sessionStorage.setItem(key, JSON.stringify(value));
    else
        sessionStorage.removeItem(key);
}

function removeCacheFromSessionStorage(key) {
    sessionStorage.removeItem(key);
}
function getCacheFromLocalStorage(key) {
    var cacheStr = localStorage.getItem(key);
    if (cacheStr)
        return JSON.parse(cacheStr);
    return null;
}

function setCache2LocalStorage(key, value) {
    if (value)
        localStorage.setItem(key, JSON.stringify(value));
    else
        localStorage.removeItem(key);
}

function removeCacheFromLocalStorage(key) {
    localStorage.removeItem(key);
}


function checkErrorDatas(data,httpOptions,errorCallback) {
    if (data.code == "success") {
        return true;
    }else{
        alert(data.errorMsg);
    }
    return false;
}
function location2Url(url){
	window.location=url;
}

