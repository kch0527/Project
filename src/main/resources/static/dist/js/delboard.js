function deleteBoard(bno){
  if(!confirm("삭제 하시겠습니까?")){
    return false;
  }else{
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", `/boards/${bno}`, true);
    xhr.onload = function () {
      location.href = `/boards`
    }
    xhr.send(null);
  }
}
