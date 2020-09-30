<template>
    <div>
        <p>
            <button class="btn btn-white btn-default btn-round" v-on:click="add()">
                <i class="ace-icon fa fa-edit"></i>
                新增
            </button>
            &nbsp;
            <button class="btn btn-white btn-default btn-round" v-on:click="list(1)">
                <i class="ace-icon fa fa-refresh"></i>
                刷新
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="list"></pagination>
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
            <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">表单</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">名称</label>
                                    <div class="col-sm-10">
                                        <input  id = "name" v-model="chapter.name" class="form-control" placeholder="名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="courseId" class="col-sm-2 control-label">课程id</label>
                                    <div class="col-sm-10">
                                        <input id="courseId" v-model="chapter.courseId" class="form-control" placeholder="课程id">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" v-on:click="save()">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.span -->


        <!-- Modal -->

    </div>

</template>

<script>
    import Pagination from "../../components/pagination";
    export default {
        name: "chapter",
        components: {Pagination},
        comments:{Pagination},
        data:function(){
            return {
                chapters:[],
                chapter:{}
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
            list:function (page) {
                let _this = this;

                _this.$axios.post("http://127.0.0.1:9000/business/chapter/list",{
                    page:page,
                    size:_this.$refs.pagination.size
                }).then((res)=>{
                    console.log('大章列表：',res.data.list)
                    _this.chapters = res.data.list;
                    _this.$refs.pagination.render(page,res.data.total)
                })
            },
            //add
            add:function () {
                let _this = this;
                $(".modal").modal("show");
            },
            //save
            save:function (data) {
                let _this = this;
                _this.$axios.post("http://127.0.0.1:9000/business/chapter/save",_this.chapter).then(
                    (res)=>{

                    }
                )
            }
        }
    }
</script>