/**
 * @author: 吴佳隆
 * @date: 2020年7月16日 下午6:08:06
 * @Description: 导入公共头部 js
 */
;
// 使用方式一
import header from "/static/js/head.import.js";
let head = document.head;
// 'beforeBegin', 'afterBegin', 'beforeEnd', 'afterEnd'
head.insertAdjacentHTML('beforeBegin', header);
// <script type="module" src="/static/js/head.js" charset="UTF-8"></script>

// 使用方式二
// 此 header 为 /static/js/head.js 中的 header 变量，注意不能写出第三方引用
// document.write(header);
// <script type="text/javascript" src="/static/js/head.js" charset="UTF-8"></script>
