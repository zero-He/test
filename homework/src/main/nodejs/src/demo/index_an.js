import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import ReactCSSTransitionGroup from 'react/lib/ReactCSSTransitionGroup';
// import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
// var ReactCSSTransitionGroup = React.addons.CSSTransitionGroup;

var INTERVAL = 2000;
var count = 0;
function content() {
    count++;
    if (count % 2 == 0) {
        return (
            <div>
                <p>我是一名在校学生，利用寒假之际，来到了散文网。说实话，散文网是一个草根写手的大平台，里面的文章千姿百态，特别是一些针砭时弊的比较新锐的文章，别的网络不敢接受，唯有散文网里能发表敢发表，我认为这是散文网的优秀之处之一。</p>
                <p>散文网里人人皆可来当编辑，这不但给了写手一个锻炼自己审批作品优劣的机会，更重要的是写手可以藉此提高自己鉴赏文学作品的能力，从而提高自己的写作能力。</p>
                <p>我发现散文网里有一个问题就是：有的写手写出来的文章没有被推荐上榜，就会想入非非的怀恨一些审批人员，光明磊落的就直接说某某审批人员的一些瑕疵，阴暗的就化作这样那样的笔名直接就大骂审批人员，我认为这样的人是不配写文章的，您认为这样的人配写文章吗？</p>
                <p>好作品人人爱读，人人都爱读好作品，您写的作品如果真的很好，还会在乎一个两个审批人员不推荐吗？难道一个两个审批人员就能遮住太阳的热量吗？</p>
                <p>我问了散文网里几个资深的审批人员，他们说一篇文章能得八十分就推荐，假如您写的文章得分可能在七十分至八十分之间，那您的这篇文章就在可推荐与不可推荐之间，这就要看您的运气了。这不怪谁，只能怪你写的文章还不足够的好，试想，您写的文章如能得九十分，我想您文章里的评论栏里应该是齐刷刷的推荐阅读了，对吗？</p>
            </div>
        );
    } else {
        return (
            <div>
                <p>六月的天气，太阳说没有就没有。不一会，厚厚墨灰色乌云里钻出豆大雨瓣重重落在整个街坊。街坊，路线运动已经进入很长时间。一些知识文人走了，去很远地方扎根。一些市井男人走了，去很远地方落户。一些知识青年走了，去很远地方安家。灶间女人，戏耍孩童，对棋爷爷，都随着走去。昔日恬静，温暖，团结，亲福，象太阳一样说没有就没有，只留下一片寂冷。</p>
                <p>起风了，卷起也不知从那儿来的尘土，纸屑，树叶…… 忽地，飞到这边青砖瓦屋，忽地，飞到那边红砖瓦屋，最后飞到几个房屋之间井院里，井院，晾在竹杆上衣服，裤子，裙子，红领巾全飞起来，院角，几枝竹子也成了风的帮凶，把木格窗台上晒的瓜皮，石桌上书本全推向屋顶。邻里走了许多，几乎没有帮衬热气，等到上夜班婶婶听到声响，衣服，裤子，裙子……早飞得无影无踪。</p>
                <p>雨终于来了，泼下来一般，雷紧紧跟在后面拼出闪光，划亮整个街坊。</p>
                <p>街坊已经没有先前干净漂亮。那屋前屋后柳树，桃树，石榴……那院墙缠绕牵牛花，刺玫瑰……给风雨击得，梢弯枝垂，藤离叶坠……青石砌的小街己经成了雨溪，上面漂浮标语、花瓣、树枝、杂物、急急流向巷口拐弯处，拐弯处阿秀站在那里不动，雨侵透她双脚，鞋子没有了，只有一只线袜套在脚上…阿秀被打到了！阿秀一句悄悄儿女情话，让闺蜜揭去，阿秀被圈入路线运动中……</p>
                <p>少年哥哥你在那里？曾经黑板萤白喃喃语爱；曾劲清河柳绿窃窃心诺；少年哥哥你在那里？停课检查批判，黑砂铸造拣石，高热炼炉拣铁帮忙，阿秀哭着，和自己一般大十六七岁男孩女孩也哭了。父亲历史问题，母亲成分问题，调皮打架，豆萌初情……都圈入路线运动中去。</p>
                <p>雨一直没停，越来越大！风橑起雨幕，一层层，一层层，把整个街坊围起来， 曚矇眬眬可见青石砌的小街延伸出来，―直延伸到那边太阳升起的地方……许久，许久，太阳终于升起来，雨走了，街坊渐渐添了温暖。</p>
                <p>知识文人回来了，市井男人回来了，知识青年回来了，老去灶间女人，长大戏耍孩子，拄着拐杖对棋爷爷，都随着回来，井院里重新有了领里帮衬热气，屋前屋后桃花、石榴花，院墙外缠绕刺玫瑰……开始鲜活起来。昔曰恬静，团结，幸福又回到整个街坊。少年哥哥你在那里？阿秀抱着少年哥哥曾劲语爱，心诺，牵着和自已一起长大男孩女孩，背起许多没有读完书卷，沿着青石砌的小街向着太阳升起地方走去。</p>
            </div>
        );
    }
}

class AnimateDemo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            current: 0
        };
    }
    componentDidMount() {
        this.interval = setInterval(this.tick, INTERVAL);
    }
    componentWillUnmount() {
        clearInterval(this.interval);
    }
    tick = () => {
        this.setState({current: this.state.current + 1});
    }
    render() {
        var style = {
            right: 0
        };
        return (
            <div>
                <ReactCSSTransitionGroup className="c-slide-box" transitionEnterTimeout={250} transitionLeaveTimeout={250} transitionName="slide-right">
                    <div key={this.state.current} className="c-slide-item" style={style}>
                        {content()}
                    </div>
                </ReactCSSTransitionGroup>
            </div>
        );
    }
}

ReactDOM.render(<AnimateDemo />, document.getElementById('container'));
