/**
 * Created by liyuanquan on 2017/10/23.
 */
var app = new Vue({
    el: "#sys_set",
    data() {
        return {
            // 超时设置
            overtime: {},
            // 定期更改设置
            regular: {},
            // 锁定账号设置
            locking: {},
            // 密码验证
            validate: {},
            // 最短密码长度设置
            minLength: "6",
            // 最长密码长度设置
            maxLength: "15",
            // 上传设置
            upload: "",
            // 页面验证
            systemRule: {
                overtime: "",
                regular: "",
                locking: "",
                validate: "",
                // 密码强度设置
                strength: [],
                // 密码长度设置
                pswlength: [],
                // 上传设置
                upload: ""
            },
            rules: {
                overtime:[{ required: true, message: '请选择', trigger: 'blur' }],
                regular:[{ required: true, message: '请选择', trigger: 'blur' }],
                locking:[{ required: true, message: '请选择', trigger: 'blur' }],
                validate:[{ required: true, message: '请选择验证类型', trigger: 'blur' }],
                upload:[{ required: true, message: '请选择', trigger: 'blur' }],
                strength: [
                    { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
                ],
                pswlength: [
                    { type: 'array', required: true, message: '请输入', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
    }
});