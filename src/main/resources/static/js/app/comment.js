var main = {
    init : function () {
        var _this = this;
        $('#btn-comment-save').on('click', function() {
            _this.save();
        });
        $('#btn-comment-update').on('click', function() {
            _this.update();
        });
        $('#btn-comment-delete').on('click', function() {
            _this.delete();
        });
    },
    save : function() {
        var data = {
            content: $('#commentContent').val(),
            created_by: $('#commentAuthor').val(),
            post_id: $('#post_id').val(),
            user_id: $('#user_id').val()
        };

        var post_id = $('#post_id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/comments',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 등록되었습니다.');
            window.location.href = '/post/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        var data = {
            content: $('#commentContent').val(),
            created_by: $('#commentAuthor').val(),
            post_id: $('#post_id').val(),
            user_id: $('#user_id').val()
        };

        var post_id = $('#post_id').val();
        var comment_id = $('#comment_id').val()

        $.ajax({
            type: 'PUT',
            url: '/api/v1/comments/'+comment_id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 수정되었습니다.');
            window.location.href = '/post/'+post_id;
        }).fail(function() {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var post_id = $('#post_id').val();
        var comment_id = $('#comment_id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/comments/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('댓글이 삭제되었습니다.');
            window.location.href = '/post/'+post_id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();
