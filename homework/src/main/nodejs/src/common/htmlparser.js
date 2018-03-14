
export function clearImgStyle(html) {
    const $html = $(`<div>${html}</div>`);
    $html.find('img').removeAttr('style');
    return $html.html();
}

export function parseImgUrls(html) {
    const $html = $(`<div>${html}</div>`);
    const imgs = [];
    $html.find('img').each((i, v) => imgs.push($(v).attr('src')));
    return imgs;
}

const IMG_REG = /<img >/
const DIRECTIVE_REG = /<!--\s+(\S+)\s*:\s*(\S+)\s*-->/
function parseHtml(html) {
    const frag = document.createElement('div');
    frag.innerHTML = html;
    $(frag).children();
}
