function memberBlock(seq){
  if(!confirm("제한 하시겠습니까?")){
    return false;
  }else{
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `/members/${seq}`, true);
    xhr.onload = function () {
      location.href = `/members`
    }
    xhr.send(null);
  }
}
