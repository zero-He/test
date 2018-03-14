<%@ page pageEncoding="UTF-8"%>
<%@ include file="/pages/common/tags.jsp"%>
    <style type="text/css">
    	.version-result{float: right;margin-right: 15px;color: #999; overflow: hidden; max-width: 60%; text-overflow: ellipsis; white-space: nowrap;}
    </style>
		<form style="display: none;" id="jForm" action="save.htm" method="post">
	    </form>
            <div class="content">
                <!-- 我要提问 || 我要回答 -->
                <div class="c-question-edit">
                    <div class="formbox">
                        <div class="description">
                            <textarea name="" id="jDescribe" maxlength="500" cols="30" rows="10" placeholder="问题描述"></textarea>
                            <span class="count"><i id="jCount">0</i>/<i>500</i></span>
                        </div>

                        <div class="choose c-arrow" id="j-choose">
                            	问题所属科目和老师 <span class="result">未选择</span>
                        </div>
                        
                        <div class="choose c-arrow" id="j-choose-version">
                            	教材版本信息 <span class="version-result">未选择</span>
                        </div>
                    </div>

                    <div class="preview">
                        <div class="c-media" id="jAudioDiv" style="display: none;">
                            <button class="media-btn">
                                <span class="waves">
                                    <b class="w1"><i></i></b>
                                    <b class="w2"><i></i></b>
                                    <b class="w3"><i></i></b>
                                </span>
                            </button>
                            <span id="jAudioLength" class="length"></span>
                            <span id="jRemoveMedia" style="color:#999" class="length">删除</span>
                        </div>

                        <ul>
                            <li>
			                    <div id="jPhotos" class="photos">
			                    </div>
			                </li>
			                <li>
			                    <div id="jCamera" class="camera">
			                    </div>
			                </li>
                            <li>
                                <div class="media" id="j-media"></div>
                            </li>
                            <li>
                                <div class="handbord" id="j-handbord"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- 选择科目及老师弹层 -->
        <div class="c-choose-box">
            <div class="inner">
                <h5><span class="cancel">取消</span>选择科目及老师<span class="save">确定</span></h5>
                <div class="choose-con" id="jListBody">
                    
                </div>
            </div>
        </div>
        
        <!-- 选择教材版本信息 -->
        <div class="c-choose-version c-hidden j-choose-version" data-v="hd" data-i="${schoolStageId}">
            <div class="inner">
                <h5><span class="cancel">取消</span>选择科目及老师<span class="save">确定</span></h5>
                <div class="choose-con">
                    <ul>
                        <li class="version-title">教材版本<span data-v="a" class="selected c-arrow-up">未选择</span></li>
                        <ul class="version-opts c-hidden" id="jPresses">
                            
                        </ul>
                        <li class="version-title">年级或课本<span data-v="b" class="selected c-arrow-up">未选择</span></li>
                        <ul class="version-opts c-hidden" id="jMaterials">
                           
                        </ul>
                        <li class="version-title">教材单元<span data-v="c" class="selected c-arrow-up">未选择</span></li>
                        <ul class="version-opts c-hidden" id="jSectionRange">
                           
                        </ul>
                        <li class="version-title">教材章节<span data-v="d" class="selected c-arrow-up">未选择</span></li>
                        <ul class="version-opts c-hidden" id="jSectionChapter">
                            
                        </ul>
                    </ul>
                </div>
            </div>
        </div>

        <!-- 手写板弹层 -->
        <div class="c-handbord">
            <div class="inner">
                <h5><span class="cancel">取消</span>请使用手写笔提问或回答<span id="jSaveHandBord" class="save">确定</span></h5>
                <div class="canvas">
                    <canvas id="board"></canvas>
                </div>
                <div class="tools">
                    <!-- <i class="pen"></i> -->
                    <i id="clean" class="clear"></i>
                </div>
            </div>
            <img hidden="hidden" alt="" id="preview" src="">
        </div>

        <!-- 录音弹层 -->
        <div class="c-recordpanel" id="jRecordDiv">
            <div class="inner">
                <h5><span class="cancel">取消</span>添加录音<span class="save">确定</span></h5>
                <div class="box">
                    <div class="tips">
                        <span id="jTips">点击开始录音最长3分钟</span>
                        <span id="jShowMediaTime"></span>
                    </div>
                    <!-- 操作区 -->
                    <table class="operation">
                        <tr>
                            <td>
                                <span id="jRecordAgain" class="record-again">重录</span>
                            </td>
                            <td>
                                <div id="jRecord" class="start-record c-record">
                                    <ul>
                                        <li></li>
                                        <li></li>
                                        <li></li>
                                        <li></li>
                                        <li></li>
                                    </ul>
                                </div>
                                <div id="jPlay" class="play" style="display: none;">
	                                <div class="m-circleprogress">
	                                    <span class="status"></span>
	                                    <div class="l">
	                                        <div class="l-circle"></div>
	                                    </div>
	                                    <div class="r">
	                                        <div class="r-circle"></div>
	                                    </div>
	                                </div>
	                            </div>
                            </td>
                            <td>
                            </td>
                        </tr>
                    </table>
                    <div id="jRecordTips" class="record-tips">
                    	注意！每次只可上传一段语音，已添加的语音会被替换。
                    </div>
            </div>
        </div>
    <div class="m-load" style="display: none;">
	    <i class="loader"></i>
	</div>