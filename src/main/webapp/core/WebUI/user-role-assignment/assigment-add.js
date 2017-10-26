/**
 * Created by admin on 2017/10/26.
 */
//用户分配角色
switch(operate){
    case 1:
        res=right.allRoleData;
        if(right.rightRowId!=undefined){
            resId=right.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
    case 2:
        res= rightBlock.allRoleData;
        if(rightBlock.rightRowId!=undefined){
            resId=rightBlock.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
    case 3:
        res= distributeUser.allRoleData;
        if(distributeUser.rightRowId!=undefined){
            resId=distributeUser.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
    case 4:
        res= rightUserBlockRole.allRoleData;
        if(rightUserBlockRole.rightRowId!=undefined){
            resId=rightUserBlockRole.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
}

//if(operate==1){
//    res=right.allRoleData;
//    if(right.rightRowId!=undefined){
//        resId=right.rightRowId;
//        resIds=resId.split(",");
//    }else{
//        resIds=[];
//    }
//}
////用户组分配角色
//else if(operate==2){
//    res= rightBlock.allRoleData;
//    if(rightBlock.rightRowId!=undefined){
//        resId=rightBlock.rightRowId;
//        resIds=resId.split(",");
//    }else{
//        resIds=[];
//    }
//}
////角色分配用户
//else if(operate==3){
//    res= distributeUser.allRoleData;
//    if(distributeUser.rightRowId!=undefined){
//        resId=distributeUser.rightRowId;
//        resIds=resId.split(",");
//    }else{
//        resIds=[];
//    }
//}
////角色分配用户组
//else if(operate==4){
//    res= rightUserBlockRole.allRoleData;
//    if(rightUserBlockRole.rightRowId!=undefined){
//        resId=rightUserBlockRole.rightRowId;
//        resIds=resId.split(",");
//    }else{
//        resIds=[];
//    }
//}
var vm=new Vue({
        el:'#depart',
        data(){
            var generateData=function(res){
                const data = [];
                for (let i = 0; i <res.length; i++) {
                    data.push({
                        // 唯一标示
                        key: res[i].rowId,
                        //显示文字
                        label: res[i].roleName,
                        //是否可点击
                        //disabled: i % 4 === 0
                    });
                }
                return data;
            };
            return {
                data: generateData(res),
                //绑定到目标标示上的数据
                value1: resIds
            };
        },
        methods:{
            conformEvent(){
                if(vm.value1.length==0){
                    vm.value1='';
                }
                if(operate==1){
                    addObj.addOk(function(){
                        var data={
                            "url":userDeverRole,
                            "jsonData":{
                                userRowId:right.userRowId,//这一条的ID
                                roleRowId:vm.value1,//拥有角色的数组
                            },
                            "obj":vm,
                            "showMsg":true
                        };
                        gmpAjax.showAjax(data,function(res){
                            console.log(res);
                            //分页跳回到第一页
                            right.searchUserRoleFirst();
                            ibcpLayer.Close(divIndex);
                        })
                    })

                }else if(operate==2){
                    var data={
                        "url":userGroupDeverRole,
                        "jsonData":{
                            userGroupRowId:rightBlock.userRowId,//这一条的ID
                            roleRowIds:vm.value1,//拥有角色的数组
                        },
                        "obj":vm,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        console.log(res);
                        //分页跳回到第一页
                        rightBlock.searchUserGroupRoleFirst();
                        ibcpLayer.Close(divIndex);
                    })
                }else if(operate==3){

                }else if(operate==4){

                }

            },
            cancel(){
                ibcpLayer.Close(divIndex);
            }
        },
    })
