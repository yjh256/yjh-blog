var main = {
    init : function () {
        var _this = this;
        $('#btn-delete').on('click', function() {
            _this.delete();
        });
    },
    delete : function() {
        var id = $('#id').val();
        message = "이 글을 정말 삭제하시겠습니까?";
        if (confirm(message) != 0) {
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
                }).done(function() {
                alert('글이 삭제되었습니다.');
                    window.location.href = '/';
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
        }
    }
};

main.init();

var page_num = document.location.href.split("?page=")[1]
$(".page-numbers").filter((index, element) => element.innerText == page_num)
    .addClass("active");

$(".modifiedDate").each((index, element) => {
    var text = element.innerText;
    element.innerText = text.replace('T', ' ');
});

$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});


