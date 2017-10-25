/**
 * Created by liyuanquan on 2017/10/24.
 */
var app = new Vue({
    el: "#per_info",
    data() {
        return {
            activeName: "info",
            info_detail: {
                // 姓名
                name: "",
                // 性别
                gender: "",
                // 身份证
                idCard: "",
                // 入职日期配置
                dateConfig: {
                    // 控件类型
                    type: "date",
                    // 占位符文字
                    placeholder: "请选择日期",
                },
                // 所属部门配置
                treeConfig: {
                    clearable: false,
                    // 显示复选框
                    checkbox: false,
                    // 展开所有节点
                    expandedAll: true,
                    // 配置显示项 必须配置！
                    defaultProps: {
                        // 树节点显示文字
                        label: 'orgName',
                        // 节点id
                        key: "orgId",
                        // 父节点信息
                        parent: "orgPid"
                    },
                    // 获取数据接口
                    url: serverPath + "/baseOrg/queryPage"
                },
                // 邮箱
                email: "",
                // 初始密码
                password: "",
                // 职务
                post: "",
                // 工号
                id: "",
                // 移动电话
                mobilePhone: "",
                // 办公电话
                officePhone: "",
                // 说明
                description: "",
                // 备注
                remarks: "",
                // 只读项
                readonly: true
            },
            psw_modify: {
                // 只读项
                readonly: true,
                // 姓名
                name: "",
                // 初始密码
                password: "",
                // 新密码
                newPassword: "",
                // 重复密码
                repeatPassword: ""
            }
        }
    },
    computed: {
        info() {
            let template = `<el-form ref="personalForm" label-width="130px" style="padding: 8px;">
                                <div class="demo_line">
                                    <hr>
                                    <span>基本信息</span>
                                </div>
                                <el-row class="row-bg">
                                    <el-col :span="11">
                                        <el-form-item label="用户姓名" prop="name">
                                            <el-input placeholder="请输入用户姓名" v-model="detail.name" ref="name"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="10">
                                        <el-form-item label="性别" prop="gender">
                                            <el-select v-model="detail.gender" placeholder="请选择性别" clearable style="width: 100%;">
                                                <el-option value="male" label="男"></el-option>
                                                <el-option value="female" label="女"></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row class="row-bg">
                                    <el-col :span="11">
                                        <el-form-item label="身份证" prop="idCard">
                                            <el-input v-model="detail.idCard" ref="idCard" :readonly="detail.readonly"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="10">
                                        <el-form-item label="入职日期" prop="hiredate">
                                            <date-time-picker :initial="detail.dateConfig"></date-time-picker>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <div class="demo_line">
                                    <hr>
                                    <span>账号信息</span>
                                </div>
                                <el-row class="row-bg">
                                    <el-col :span="11">
                                        <el-form-item label="职务" prop="post">
                                            <el-input placeholder="请输入职务" v-model="detail.post" ref="post"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="10">
                                        <el-form-item label="所属部门" prop="belong" class="demo-input-suffix">
                                            <select-tree :initial="detail.treeConfig"></select-tree>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row class="row-bg">
                                    <el-col :span="11">
                                        <el-form-item label="用户工号" prop="id">
                                            <el-input v-model="detail.id" ref="id" :readonly="detail.readonly"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="10">
                                        <el-form-item label="初始密码" prop="password">
                                            <el-input v-model="detail.password" ref="password" :readonly="detail.readonly"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row class="row-bg">
                                    <el-col :span="11">
                                        <el-form-item label="邮箱" prop="email">
                                            <el-input placeholder="请输入邮箱" v-model="detail.email" ref="email"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <div class="demo_line">
                                    <hr>
                                    <span>联系信息</span>
                                </div>
                                <el-row class="row-bg">
                                    <el-col :span="11">
                                        <el-form-item label="移动电话" prop="mobilePhone">
                                            <el-input placeholder="请输入移动电话" v-model="detail.mobilePhone" ref="mobilePhone"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="10">
                                        <el-form-item label="办公电话" prop="officePhone">
                                            <el-input placeholder="请输入办公电话" v-model="detail.officePhone" ref="officePhone"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <div class="demo_line">
                                    <hr>
                                    <span>其他信息</span>
                                </div>
                                <el-row class="row-bg">
                                    <el-col :span="24">
                                        <el-form-item label="说明" prop="description">
                                            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入说明" v-model="detail.description" ref="description">
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24">
                                        <el-form-item label="备注" prop="remarks">
                                            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入备注" v-model="detail.remarks" ref="remarks">
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-form>`;
            let props = ["detail"];
            return {
                template,
                props
            }
        },
        psw() {
            let template = `<el-col :span="12" :offset="6">
                                <el-form label-width="130px" style="padding: 8px;">
                                    <el-form-item label="用户姓名">
                                        <el-input v-model="modify.name" ref="" :readonly="modify.readonly"></el-input>
                                    </el-form-item>
                                    <el-form-item label="初始密码" prop="">
                                        <el-input v-model="modify.password" ref="" placeholder="请输入初始密码"></el-input>
                                    </el-form-item>
                                    <el-form-item label="密码" prop="">
                                        <el-input v-model="modify.newPassword" placeholder="请输入新密码" ref=""></el-input>
                                    </el-form-item>
                                    <el-form-item label="请重复密码" prop="">
                                        <el-input v-model="modify.repeatPassword" placeholder="请再次输入新密码" ref=""></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary">保存</el-button>
                                    </el-form-item>
                                </el-form>
                            </el-col>`;
            let props = ["modify"];
            return {
                template,
                props
            }
        }
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event);
        }
    }
});