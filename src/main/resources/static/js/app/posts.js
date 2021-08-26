const posts = {
    dates : [],
    init : function () {
        const _this = this;
        $('#btn-delete').on('click', function() {
            _this.delete();
        });
        _this.dateTrimFirst();
        window.addEventListener("resize", _this.dateTrim.bind(_this));
        _this.activePage();
        _this.fileSelected();
    },
    delete : function() {
        const id = $('#id').val();
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
    },
    dateTrimFirst : function() {
        $(".modifiedDate").each((index, element) => {
            let text = element.innerText;
            this.dates.push(text.replace('T', ' ').slice(0, 19));
        });
        this.dateTrim();
    },
    dateTrim : function() {
        $(".modifiedDate").each((index, element) => {
             if (window.innerWidth > 900) {
                element.innerText = this.dates[index];
             } else {
                element.innerText = this.dates[index].slice(0, 11);
             }
        });
    },
    activePage : function() {
        const page_num = document.location.href.split("?page=")[1]
        $(".page-numbers").filter((index, element) => element.innerText == page_num).addClass("active");
    },
    fileSelected : function() {
        $(".custom-file-input").on("change", function() {
          const fileName = $(this).val().split("\\").pop();
          $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
        });
    }
};

posts.init();








