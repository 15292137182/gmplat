/**
 * Created by admin on 2017/10/26.
 */
//用户分配角色
switch(operate){
    //用户分配角色
    case 1:
        res=right.allRoleData;
        name=right.allRoleData;
        if(right.rightRowId!=undefined){
            resId=right.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
        //用户组分配角色
    case 2:
        res= rightBlock.allRoleData;
        if(rightBlock.rightRowId!=undefined){
            resId=rightBlock.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
        //角色分配用户
    case 3:
        res= distributeUser.allRoleData;
        if(distributeUser.rightRowId!=undefined){
            resId=distributeUser.rightRowId;
            resIds=resId.split(",");
        }else{
            resIds=[];
        }
    break;
    //角色分配用户组
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
var vm=new Vue({
        el:'#depart',
        data(){
            var generateData=function(res){
                const data = [];
                for (let i = 0; i <res.length; i++) {
                    if(operate==3){
                        data.push({
                            // 唯一标示
                            key: res[i].rowId,
                            //显示文字
                            label: res[i].name,
                            //是否可点击
                            //disabled: i % 4 === 0
                        });
                    }
                    if(operate==4){
                        data.push({
                            // 唯一标示
                            key: res[i].rowId,
                            //显示文字
                            label: res[i].groupName,
                            //是否可点击
                            //disabled: i % 4 === 0
                        });
                    }else if(operate==1||operate==2){
                        data.push({
                            // 唯一标示
                            key: res[i].rowId,
                            //显示文字
                            label: res[i].roleName,
                            //label: res[i].name,
                            //是否可点击
                            //disabled: i % 4 === 0
                        });
                    }

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
                        //分页跳回到第一页
                        rightBlock.searchUserGroupRoleFirst();
                        ibcpLayer.Close(divIndex);
                    })
                }else if(operate==3){
                    var data={
                        "url": roleDeverUser,
                        "jsonData":{
                            roleRowId:distributeUser.userRowId,//这一条的ID
                            userRowIds:vm.value1,//拥有角色的数组
                        },
                        "obj":vm,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        //分页跳回到第一页
                        distributeUser.searchRoleUserFirst();
                        ibcpLayer.Close(divIndex);
                    })
                }else if(operate==4){
                    var data={
                        "url": roleDeverUserGroup,
                        "jsonData":{
                            roleRowId :rightUserBlockRole.userRowId,//这一条的ID
                            userGroupRowIds:vm.value1,//拥有角色的数组
                        },
                        "obj":vm,
                        "showMsg":true
                    };
                    gmpAjax.showAjax(data,function(res){
                        //分页跳回到第一页
                        rightUserBlockRole.searchRoleUserGroupFirst();
                        ibcpLayer.Close(divIndex);
                    })
                }

            },
            cancel(){
                ibcpLayer.Close(divIndex);
            }
        },
    })
