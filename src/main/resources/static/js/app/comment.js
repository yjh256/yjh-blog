const comments = {
    dates : [],
    init : function () {
        const _this = this;
        $('#btn-comment-save').on('click', function() {
            _this.save();
        });
        const updateButtons = document.querySelectorAll('.btn-comment-update');
        updateButtons.forEach(function(item) {
            item.addEventListener('click', function() {
                const form = this.closest('form');
                _this.update(form);
            });
        });
        $('#btn-comment-update').on('click', function() {
            _this.update();
        });
        $('#btn-comment-delete').on('click', function() {
            _this.delete();
        });
        _this.onlyForAuthor();
        _this.dateTrim();
    },
    save : function() {
        const data = {
            content: $('#save-comment-content').val(),
            created_by: $('#save-comment-author').val(),
            post_id: $('#post_id').val(),
            user_id: $('#user_id').val()
        };

        const post_id = $('#post_id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/comments',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 등록되었습니다.');
            window.location.href = '/posts/'+post_id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function(form) {
        const data = {
            content: form.querySelector('#comment-content').value,
            created_by: $('#comment-author').val(),
            post_id: $('#post_id').val(),
            user_id: $('#user_id').val()
        };

        const post_id = $('#post_id').val();
        const comment_id = $('#comment_id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/comments/'+comment_id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 수정되었습니다.');
            window.location.href = '/posts/'+post_id;
        }).fail(function() {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        const post_id = $('#post_id').val();
        const comment_id = $('#comment_id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/comments/'+comment_id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('댓글이 삭제되었습니다.');
            window.location.href = '/posts/'+post_id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    onlyForAuthor : function() {
        const author = $("#save-comment-author").val();
        $(".only-for-author").filter(function() {
            const created_by = $(this).siblings('.commentsAuthor').text();
            return created_by == author;
        }).attr("hidden", false);
    },
    dateTrim : function() {
        $(".comments-modifiedDate").each((index, element) => {
            let text = element.innerText;
            element.innerText = text.replace('T', ' ').slice(0, 19);
        });
    }
};

comments.init();



