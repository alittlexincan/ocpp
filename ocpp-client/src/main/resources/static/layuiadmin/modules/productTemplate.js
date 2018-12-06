
layui.define(function(exports){

    /**
     * 灾种相关数据转换
     */
    var productTemplate = {
        /**
         * 决策服务产品
         * @param option
         * @returns {*}
         */
        "getDecisionServiceProductTemplate": function(option) {
            return '<p style="text-align:center">\n' +
                '    <strong><span style="font-size:29px;font-family:宋体;color:red"></span></strong>\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <span style="font-size:80px;font-family:宋体;color:red;position:relative;top:1px;letter-spacing:-1px">芒种专题</span>\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <strong><span style="font-size:29px;font-family:宋体;color:red"></span></strong>' +
                '<span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;">〔</span>' +
                '<span style="text-align: center; font-size: 24px;">2018</span>' +
                '<span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;">〕第</span>' +
                '<span style="text-align: center; font-size: 24px; color: red;">01</span>' +
                '<span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;">期</span>\n' +
                '</p>\n' +
                '<p style="display: inline-block;width: 100%;text-align:center;">\n' +
                '    <span style="display: inline-block;font-size:21px;font-family:仿宋_GB2312; text-align: justify; float: left; ">'+option.group+'</span>' +
                '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                '<span style="display: inline-block;font-size:21px;font-family:仿宋_GB2312; text-align: justify; float: right;">签发人:XXX</span>\n' +
                '</p>\n' +
                '<p style="border-top: 2px solid red; border-bottom: none; border-right: none;border-left: none;align-content: center;">\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <span style="font-size:23px">一周天气分析</span>\n' +
                '</p>\n' +
                '<p style="text-indent:36px">\n' +
                '    未来一周，前期冷空气较强，降温、降雨；中后期暖空气势利明显加强，将转为升温和少雨天气时段。气温明显升高，风力较大，对春播有利，需关注局地阵雨。请各农区未播种地块抓住晴好天气时段适时播种，已播种地块做好田间管理，水稻产区根据当地具体情况利用有利时机适时移栽。\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p style="text-align: right;">' +
                '<span style="font-family: 仿宋_GB2312; font-size: 21px; text-align: justify;">'+option.group+'</span><span style="font-family: 仿宋_GB2312; font-size: 21px; text-align: justify;"></span>' +
                '</p>\n' +
                '<p style="text-align: right;">\n' +
                '    <span style="font-family: 仿宋_GB2312; font-size: 21px; text-align: justify;">'+option.dateTime+'</span>\n' +
                '</p>\n' +
                '<p style="text-indent:13px;line-height:37px">\n' +
                '    <span style="font-size:21px;font-family:仿宋_GB2312;"><br/></span>\n' +
                '</p>\n' +
                '<p style="text-indent:13px;line-height:37px">\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p style="text-indent:13px;line-height:37px">\n' +
                '    <span style="font-size:21px;font-family:仿宋_GB23129;"></span>\n' +
                '</p>\n' +
                '<p style="text-indent:0">\n' +
                '    <strong><span style="font-size:21px;font-family:仿宋_GB2312;">24小时值班电话：XXXX-XXXXXXX 审核人：XX</span></strong>\n' +
                '</p>\n' +
                '<hr/>\n' +
                '<p style="text-indent: 0px;">\n' +
                '    <span style="font-size: 21px; font-family: 仿宋_GB2312, serif;">报送：永航、奕生、庆利、</span><span style="font-size: 21px; font-family: 仿宋_GB2312, serif;">吴轼、</span><span style="font-size: 21px; font-family: 仿宋_GB2312, serif;">嘉文、张锐、武林、锡川、小勇同志、市委办、市府办、市委总值班室、航展执委会、市三防办、市应急办、市文体旅游局、横琴新区、各区政府（管委会）、市各有关部门、市各新闻媒体</span>\n' +
                '</p>';
        },
        /**
         * 预报产品
         * @param option
         * @returns {*}
         */
        "getForecastProductTemplate": function(option) {
            return '<p style="text-align:center">' +
                '<strong><span style="font-size:29px;font-family:宋体;color:red"></span></strong>' +
                '</p>' +
                '<p style="text-align:center"> ' +
                '<span style="font-size:80px;font-family:宋体;color:red;position:relative;top:1px;letter-spacing:-1px">气象服务预报</span> ' +
                '</p> ' +
                '<p style="text-align:center"> ' +
                '<strong><span style="font-size:29px;font-family:宋体;color:red"></span></strong><br/> ' +
                '</p>' +
                '<p style="text-align:center">'+option.group+'&nbsp;&nbsp;签发人:XXX&nbsp;&nbsp;'+option.dateTime+'</p>\n' +
                '<p style="border-top: 2px solid red; border-bottom: none; border-right: none;border-left: none;align-content: center;">  </p>\n' +
                '<p style="text-align:center">\n' +
                '    <span style="font-size:23px">一周天气分析</span>\n' +
                '</p>\n' +
                '<p style="text-indent:36px">\n' +
                '    未来一周，前期冷空气较强，降温、降雨；中后期暖空气势利明显加强，将转为升温和少雨天气时段。气温明显升高，风力较大，对春播有利，需关注局地阵雨。请各农区未播种地块抓住晴好天气时段适时播种，已播种地块做好田间管理，水稻产区根据当地具体情况利用有利时机适时移栽。\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <strong><span style="font-family: 宋体">具体天气预报</span></strong>\n' +
                '</p>\n' +
                '<table align="center">\n' +
                '    <tbody>\n' +
                '        <tr class="firstRow">\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    时间\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    天气\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    风向风速\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    气温(℃)\n' +
                '                </p>\n' +
                '            </td>\n' +
                '        </tr>\n' +
                '        <tr>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    16日\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    阵雨转多云\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    偏西风3-4级\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    6～13\n' +
                '                </p>\n' +
                '            </td>\n' +
                '        </tr>\n' +
                '        <tr>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    17日\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    晴\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    西南风2-3级\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    6～26\n' +
                '                </p>\n' +
                '            </td>\n' +
                '        </tr>\n' +
                '        <tr>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    18日\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    晴\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    西南风2-3级\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    6～26\n' +
                '                </p>\n' +
                '            </td>\n' +
                '        </tr>\n' +
                '        <tr>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    19日\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    晴\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    西南风2-3级\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    6～26\n' +
                '                </p>\n' +
                '            </td>\n' +
                '        </tr>\n' +
                '        <tr>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    20日\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    晴\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    西南风2-3级\n' +
                '                </p>\n' +
                '            </td>\n' +
                '            <td width="148" style="padding: 1px;">\n' +
                '                <p style="text-align:center">\n' +
                '                    6～26\n' +
                '                </p>\n' +
                '            </td>\n' +
                '        </tr>\n' +
                '    </tbody>\n' +
                '</table>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>';
        },
        /**
         * 预警产品
         * @param option
         * @returns {*}
         */
        "getWarnProductTemplate": function(option) {
            return '<p style="text-align:center">\n' +
                '    <strong><span style="font-size:29px;font-family:宋体;color:red"></span></strong>\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <span style="font-size:80px;font-family:宋体;color:red;position:relative;top:1px;letter-spacing:-1px">预警服务专报</span>\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <strong><span style="font-size:29px;font-family:宋体;color:red"></span></strong><span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;">〔</span><span style="text-align: center; font-size: 24px;">2018</span><span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;">〕第</span><span style="text-align: center; font-size: 24px; color: red;">01</span><span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;">期</span>\n' +
                '</p>\n' +
                '<p>' +
                '<span style="text-align: center; font-size: 24px; font-family: 宋体; color: red;"><br/></span>' +
                '</p>\n' +
                '<p style="border-top: 2px solid red; border-bottom: none; border-right: none;border-left: none;align-content: center;">' +
                '   <br/>\n' +
                '</p>\n' +
                '<p style="text-align:center">\n' +
                '    <span style="font-size:23px">一周天气分析</span>\n' +
                '</p>\n' +
                '<p style="text-indent:36px">\n' +
                '    未来一周，前期冷空气较强，降温、降雨；中后期暖空气势利明显加强，将转为升温和少雨天气时段。气温明显升高，风力较大，对春播有利，需关注局地阵雨。请各农区未播种地块抓住晴好天气时段适时播种，已播种地块做好田间管理，水稻产区根据当地具体情况利用有利时机适时移栽。\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p>\n' +
                '    <br/>\n' +
                '</p>\n' +
                '<p style="text-align: right;">' +
                '<span style="font-family: 仿宋_GB2312; font-size: 21px; text-align: justify;">'+option.group+'</span><span style="font-family: 仿宋_GB2312; font-size: 21px; text-align: justify;"></span>' +
                '</p>\n' +
                '<p style="text-align: right;">\n' +
                '    <span style="font-family: 仿宋_GB2312; font-size: 21px; text-align: justify;">'+option.dateTime+'</span>\n' +
                '</p>';
        }
    };
    //输出test接口
    exports('productTemplate', productTemplate);
});