/**
 * 异步请求模块
 * @param exports
 * @returns
 */
layui.define(['layer'], function(exports) {
    var $ = layui.jquery, layer = layui.layer;
    var request = {
            baseUrl: function() {
                return window.location;
            },
            loading: function(elem) { // 加载
                elem.append(this.elemLoad = $('<i class="layui-anim layui-anim-rotate layui-anim-loop layui-icon layui-icon-loading layadmin-loading"></i>'));
            },
            get: function(options) { // GET 请求
                options.type = 'get';
                ajaxRequest(options);
            },
            post: function(options) { // POST 请求
                options.type = 'post';
                ajaxRequest(options);
            }, 
            corsGet: function(options) { // 跨域 GET 请求
                options.type = 'get';
                options.xhrFields.withCredentials = true;
                ajaxRequest(options);
            },
            corsPost: function(options) { // 跨域 POST 请求
                options.type = 'post';
                options.xhrFields.withCredentials = true;
                ajaxRequest(options);
            }
    };
    
    // 对外接口
    exports('request', request);
    
    // 执行请求
    function ajaxRequest(options) {
        var success = options.success; // 请求成功回调函数
        
        options.defaultSuccessMsg = options.defaultSuccessMsg || true; // success 中默认只处理 status 为 200 的情况，如果设置为 false，表示 success 中处理全部成功请求事件
        options.data = options.data || {}; // 如果没有传入请求数据，将请求数据设置为 {}
        options.headers = options.headers || {}; // 如果 headers 为空，设置为 {}
        
        delete options.success;
        delete options.error;
        return $.ajax($.extend({
            type: 'get',
            dataType: 'json',
            success: function(res) {
                // 只有 response 的 code 一切正常才执行 success
                if (res.status == 200 || !options.defaultSuccessMsg) {
                    var callbacks = $.Callbacks();
                    // 只要 http 状态码正常，无论 response 的 code 是否正常都执行 success
                    callbacks.add(success);
                    callbacks.fire(res);
                } else { // 其它异常
                    errorMsg('<cite>Error：</cite> ' + (res.message || '操作失败'));
                }
            },
            error: function(e, code) {
                errorMsg('请求异常，请重试<br><cite>错误信息：</cite>' + code);
            }
        }, options));
    }
    
    // 错误提示
    function errorMsg(msg) {
        layer.msg(msg, {
            icon: 5,
            time: 2000,
            anim: 6
        });
    }
    
});