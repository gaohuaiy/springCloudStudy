<template>
    <div>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>名称</th>
                <th>课程ID</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
                <tr v-for="chapter in chapters">
                    <td>{{chapter.id}}</td>
                    <td>{{chapter.name}}</td>
                    <td>{{chapter.courseId}}</td>
                    <td>
                        <div class="hidden-sm hidden-xs btn-group">
                            <button class="btn btn-white btn-xs btn-info btn-round">
                                小节
                            </button>&nbsp;
                            <button  class="btn btn-white btn-xs btn-info btn-round">
                                编辑
                            </button>&nbsp;
                            <button  class="btn btn-white btn-xs btn-warning btn-round">
                                删除
                            </button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div><!-- /.span -->
</template>

<script>
    export default {
        name: "chapter",
        data:function(){
            return {
                chapters:[]
            }
        },
        mounted: function () {
            let _this = this;
            _this.list();
            // sidebar激活样式方法一
            //this.$parent.activeSidebar("business-chapter-sidebar");
        },
        methods: {
            //列表
            list:function () {
                let _this = this;
                _this.$axios.post("http://127.0.0.1:9000/business/chapter/list",{
                    page:1,
                    size:1
                }).then((res)=>{
                    console.log('大章列表：'+res)
                    _this.chapters = res.data.list;
                })
            }
        }
    }
</script>