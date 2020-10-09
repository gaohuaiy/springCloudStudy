<template>
    <div>
        <p>
            <button v-on:click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit"></i>
                新增
            </button>
            &nbsp;
            <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh"></i>
                刷新
            </button>
        </p>

        <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

        <!--<table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>昵称</th>
                <th>头像</th>
                <th>职位</th>
                <th>座右铭</th>
                <th>简介</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="teacher in teachers">
                <td>{{teacher.id}}</td>
                <td>{{teacher.name}}</td>
                <td>{{teacher.nickname}}</td>
                <td>{{teacher.image}}</td>
                <td>{{teacher.position}}</td>
                <td>{{teacher.motto}}</td>
                <td>{{teacher.intro}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>-->
        <div class="row">
            <div v-for="teacher in teachers" class="col-md-3">
                <div>
                    <div>
        <span class="profile-picture">
            <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty"
                 alt="Alex's Avatar" src="/static/image/讲师头像/头像1.jpg">
            <img v-show="teacher.image" class="editable img-responsive editable-click editable-empty"
                 alt="Alex's Avatar" v-bind:src="teacher.image">

        </span>

                        <div class="space-4"></div>

                        <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                            <div class="inline position-relative">
                                <a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                                    <i class="ace-icon fa fa-circle light-green"></i>
                                    &nbsp;
                                    <span class="white">{{teacher.position}}</span>
                                </a>

                                <!--<ul class="align-left dropdown-menu dropdown-caret dropdown-lighter">
                                    <li class="dropdown-header"> Change Status</li>

                                    <li>
                                        <a href="#">
                                            <i class="ace-icon fa fa-circle green"></i>
                                            &nbsp;
                                            <span class="green">Available</span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <i class="ace-icon fa fa-circle red"></i>
                                            &nbsp;
                                            <span class="red">Busy</span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <i class="ace-icon fa fa-circle grey"></i>
                                            &nbsp;
                                            <span class="grey">Invisible</span>
                                        </a>
                                    </li>
                                </ul>-->
                            </div>
                        </div>
                    </div>

                    <div class="space-6"></div>

                   <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
                       <i class="ace-icon fa fa-user"></i>
                       {{teacher.name}}【{{teacher.nickname}}】
                   </a>

                    <div class="space-4"></div>

                    <div class="profile-contact-info">

                        <div class="space-6"></div>

                        <div class="profile-social-links align-center">
                            <button v-on:click="edit(teacher)" class="btn btn-xs btn-info"><i class="ace-icon fa fa-pencil bigger-120"></i></button>
                            &nbsp;<button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger"><i class="ace-icon fa fa-trash-o bigger-120"></i></button>
                        </div>
                    </div>

                    <!--<div class="hr hr12 dotted"></div>

                    <div class="clearfix">
                        <div class="grid2">
                            <span class="bigger-175 blue">25</span>

                            <br>
                            Followers
                        </div>

                        <div class="grid2">
                            <span class="bigger-175 blue">12</span>

                            <br>
                            Following
                        </div>
                    </div>

                    <div class="hr hr16 dotted"></div>-->
                </div>
            </div>
        </div>


        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">昵称</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.nickname" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.image" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">职位</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.position" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">座右铭</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.motto" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">简介</label>
                                <div class="col-sm-10">
                                    <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>

<script>
    import Pagination from "../../components/pagination";

    export default {
        components: {Pagination},
        name: "business-teacher",
        data: function () {
            return {
                teacher: {},
                teachers: [],
            }
        },
        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
            // sidebar激活样式方法一
            // this.$parent.activeSidebar("business-teacher-sidebar");

        },
        methods: {
            /**
             * 点击【新增】
             */
            add() {
                let _this = this;
                _this.teacher = {};
                $("#form-modal").modal("show");
            },

            /**
             * 点击【编辑】
             */
            edit(teacher) {
                let _this = this;
                _this.teacher = $.extend({}, teacher);
                $("#form-modal").modal("show");
            },

            /**
             * 列表查询
             */
            list(page) {
                let _this = this;
                Loading.show();
                _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.teachers = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);

                })
            },

            /**
             * 点击【保存】
             */
            save() {
                let _this = this;

                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.teacher.name, "姓名")
                    || !Validator.length(_this.teacher.name, "姓名", 1, 50)
                    || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
                    || !Validator.length(_this.teacher.image, "头像", 1, 100)
                    || !Validator.length(_this.teacher.position, "职位", 1, 50)
                    || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
                    || !Validator.length(_this.teacher.intro, "简介", 1, 500)
                ) {
                    return;
                }

                Loading.show();
                _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/save', _this.teacher).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功！");
                    } else {
                        Toast.warning(resp.message)
                    }
                })
            },

            /**
             * 点击【删除】
             */
            del(id) {
                let _this = this;
                Confirm.show("删除讲师后不可恢复，确认删除？", function () {
                    Loading.show();
                    _this.$axios.delete(process.env.VUE_APP_SERVER + '/business/admin/teacher/delete/' + id).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });
            }
        }
    }
</script>