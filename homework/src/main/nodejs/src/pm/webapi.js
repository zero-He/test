

export default {
    getInitData: function(callback) {
        callback(Csts.childs);
    },
    fetchSubjs: function(userId) {
        return $.post('manager.htm', {
            userId: userId
        });
    }
}
