function boardblockclear(bno){
  if(!confirm("제한을 해제 하시겠습니까?")){
    return false;
  }else{
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `/boards/${bno}`, true);
    xhr.onload = function () {
      location.href = `/boards`
    }
    xhr.send(null);
  }
}
