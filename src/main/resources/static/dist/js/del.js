function deleteConfirm(idx){
  if(!confirm("삭제 하시겠습니까?")){
    return false;
  }else{
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", `/members/${idx}`, true);
    xhr.onload = function () {
      location.href = `/members`
    }
    xhr.send(null);
  }
}
