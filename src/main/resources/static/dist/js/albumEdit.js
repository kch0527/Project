let frm = $("#frm");
let $origFileDiv = $(".custom-file");
let fileMaxCnt = 3, fileMaxSize = 10485760, fileAllowExt = ["jpg","jpeg","png"];
let deleteFileIdArr = [];

function fnChngFile(obj) {
  let fileObj  = $(obj)[0];
  let fileVal	= fileObj.files[0].name;
  let fileSize	= fileObj.files[0].size;
  let fileExt	= fileVal.substring(fileVal.lastIndexOf(".") + 1, fileVal.length);
  let flag = true;

  if (fileAllowExt.indexOf(fileExt.toLowerCase()) < 0) {
    alert("It is not a registrable extension.");
  } else if (fileSize > fileMaxSize) {
    alert("Attachments can be registered up to 10MB.");
  } else if (($(".fileList").length + $(".custom-file-input").length) > 3) {
    alert("이미지가 3개를 넘어 추가가 불가능합니다.");
  } else {
    flag = false;
    $(obj).next("label").text(fileVal);
  }

  if (flag) {
    $(obj).val("");
    $(obj).next("label").text("Choose file");
  }
}

function fnViewDelete() {
  if (confirm("삭제할까요?")) {
    let frm = $("#frm");
    frm.attr("action","/album/view/delete");
    frm.submit();
  }
}

