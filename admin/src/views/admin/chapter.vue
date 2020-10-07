<template>
    <div>
        <h4 class="lighter">
            <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
            <router-link to="/business/course" href="#modal-wizard" data-toggle="modal" class="pink">{{course.name}}</router-link>
        </h4>
        <hr>
        <p>
            <router-link class="btn btn-white btn-default btn-round" to="/business/course">
                <i class="ace-icon fa fa-arrow-left"></i>
                返回
            </router-link>
            &nbsp;<button class="btn btn-white btn-default btn-round" v-on:click="add()">
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
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <tr v-for="chapter in chapters">
                    <td>{{chapter.id}}</td>
                    <td>{{chapter.name}}</td>
                    <td>
                        <div class="hidden-sm hidden-xs btn-group">
                            <button class="btn btn-white btn-xs btn-info btn-round" v-on:click="toSection(chapter)">
                                小节
                            </button>&nbsp;
                            <button class="btn btn-white btn-xs btn-info btn-round" v-on:click="edit(chapter)">
                                编辑
                            </button>&nbsp;
                            <button class="btn btn-white btn-xs btn-warning btn-round" v-on:click="del(chapter)">
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
                                        <input id="name" v-model="chapter.name" class="form-control" placeholder="名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">课程</label>
                                    <div class="col-sm-10">
                                       <p class="form-control-static">{{course.name}}</p>
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
        comments: {Pagination},
        data: function () {
            return {
                chapters: [],
                chapter: {},
                course:{}
            }
        },
        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 5;
            let course = SessionStorage.get("course") || {};//{}空对象，不会报空指针异常
            if (Tool.isEmpty(course)){
                _this.$router.push("/welcome")
            }
            _this.course = course;

            _this.list();
            // sidebar激活样式方法一
            //this.$parent.activeSidebar("business-chapter-sidebar");
        },
        methods: {
            //列表
            list: function (page) {
                let _this = this;
                console.log(_this.course)

                _this.$axios.post("http://127.0.0.1:9000/business/chapter/list", {
                    page: page,
                    size: _this.$refs.pagination.size,
                    courseId: _this.course.id
                }).then((response) => {
                    let resp = response.data;
                    console.log('大章列表：', resp.content.list)
                    _this.chapters = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },
            //add
            add: function () {
                let _this = this;
                _this.chapter = {};
                $("#myModal").modal("show");
            },
            edit: function (chapter) {
                let _this = this;
                _this.chapter = $.extend({}, chapter);
                $("#myModal").modal("show");
            },
            toSection:function (chapter) {
                let _this = this;
                SessionStorage.set("chapter",chapter);
                _this.$router.push("/business/section");
            },
            //save
            save: function (data) {
                let _this = this;

                //保存校验
                if (!Validator.require(_this.chapter.name,"名称")
                ){
                    return;
                }
                _this.chapter.courseId = _this.course.id;
                Loading.show();
                _this.$axios.post("http://127.0.0.1:9000/business/chapter/save", _this.chapter).then(
                    (response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            $("#myModal").modal("hide");
                            _this.list(1);
                            Toast.success("保存成功！！")
                        }else {
                            Toast.warning(resp.massage);
                        }
                    }
                )
                Loading.hide();
            },
            del:function (chapter) {
                let _this = this;

                Swal.fire({
                    title: '确定删除?',
                    text: "删除后不可恢复，确认删除!",
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '是，删除它!'
                }).then((result) => {
                    if (result.value) {

                            _this.$axios.post("http://127.0.0.1:9000/business/chapter/delete",chapter).then(
                                (response) => {
                                    let resp = response.data;
                                    if (resp.success) {
                                        _this.list(1);
                                    }
                                }
                            )
                        Swal.fire(
                            '已删除！',
                            '该条数据已删除',
                            '成功'
                        )
                    }
                })

            }
        }
    }
</script>