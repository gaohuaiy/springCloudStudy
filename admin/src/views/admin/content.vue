<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink">{{course.name}}</router-link>
    </h4>
    <hr>
    <file v-bind:input-id="'content-file-upload'"
          v-bind:text="'上传文件'"
          v-bind:suffixs="['png','jpg','jpeg','mp4','flv']"
          v-bind:use="FILE_USE.COURSE.key"
          v-bind:after-upload="afterUploadContentFile"></file>
    <table id="file-table" class="table table-bordered table-hover" >
      <thead>
      <tr>
        <th>名称</th>
        <th>地址</th>
        <th>大小</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(f,i) in files" v-bind:key="f.id">
        <td>{{f.name}}</td>
        <td>{{f.url}}</td>
        <td>{{f.size | formatFileSize}}</td>
        <td>
          <button v-on:click="delFile(f)" class="btn btn-white btn-xs btn-warning">
            <i class="ace-icon fa fa-times red2">删除</i>
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <form class="form-horizontal">
      <div class="form-group">
        <div class="col-lg-12">
          {{saveContentLabel}}
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-12">
          <div id="content"></div>
        </div>
      </div>
    </form>
    <p>
      <button v-on:click="saveContent()" type="button" class="btn btn-primary">保存</button>
      <router-link to="/business/course" type="button" class="btn btn-default" data-dismiss="modal">
        <i class="ace-icon fa fa-times">返回课程</i>
      </router-link>
    </p>
  </div>
</template>

<script>
  import File from "../../components/file";
  export default {
    components: {File},
    name: "business-course-content",
    data: function() {
      return {
        course: {},
        saveContentLabel:"",
        FILE_USE:FILE_USE,
        files:[],
        saveContentInterval:{},
      }
    },
    mounted: function() {
      let _this = this;
      let course = SessionStorage.get(SESSION_KEY_COURSE) || {};//{}空对象，不会报空指针异常
      if (Tool.isEmpty(course)){
        _this.$router.push("/welcome")
      }
      _this.course = course;
      _this.init();
      // sidebar激活样式方法一
       this.$parent.activeSidebar("business-course-sidebar");

    },destroyed() {
      let _this = this;
      console.log("组件销毁");
      clearInterval(_this.saveContentInterval);
    },
    methods: {

      init(){
        let _this = this;
        let course = _this.course;
        let id = course.id;
        $("#content").summernote({
          focus:true,
          height: 300
        });
        //先清空历史文本
        $("#content").summernote('code','');
        _this.saveContentLabel = "";
        //加载内容文件列表
        _this.listContentFiles();
        Loading.show();
        _this.$axios.get(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/'+id, )
                .then((response)=>{
                  Loading.hide();
                  let resp = response.data;
                  if (resp.success){
                    $("#course-content-model").modal({backdrop: 'static',keyboard: false}).show();
                    if (resp.content){
                      $("#content").summernote('code',resp.content.content);
                    }

                    //定时任务
                    _this.saveContentInterval = setInterval(function () {
                      _this.saveContent();
                    },5000);
                  }else {
                    Toast.warning(resp.msg);
                  }

                })

      },
      saveContent(){
        let _this = this;
        let content = $("#content").summernote("code");
        Loading.show();
        _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content',{
          id: _this.course.id,
          content: content
        } ).then(
                (response)=>{
                  Loading.hide();
                  let resp = response.data;
                  if (resp.success){
                    //$("#course-content-model").modal("hide");
                    //Toast.success("保存成功！");
                    let now = Tool.dateFormat("yy/MM/dd hh:mm:ss");
                    _this.saveContentLabel = "最后保存时间："+now;

                  }else {
                    Toast.warning(resp.msg);
                  }

                })

      },
      afterUploadContentFile(resp){
        let _this = this;
        console.log("开始保存文件记录");
        let file = resp.content;
        file.courseId = _this.course.id;
        file.url = file.path;
        _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/save/',file )
                .then((response)=>{
                  Loading.hide();
                  let resp = response.data;
                  if (resp.success){
                    Toast.success("文件上传成功");
                    _this.files.push(resp.content);
                  }

                });
      },
      delFile(f){
        let _this = this;
        Confirm.show("删除后不可恢复，确认删除？",function () {
          _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/delete/'+f.id, )
                  .then((response)=>{
                    let resp = response.data;
                    if (resp.success){
                      Toast.success("删除成功");
                      Tool.removeObj(_this.files,f);
                    }

                  });
        });

      },
      listContentFiles(){
          let _this = this;
          _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/list/'+_this.course.id, )
                .then((response)=>{
                  let resp = response.data;
                  if (resp.success){
                    _this.files = resp.content;
                  }
                });
      }
    }
  }
</script>
<style scoped> /*scoped 样式只回渲染当前组件，防止污染其他组件的样式*/
  caption h3{
    font-size: 20px;
  }
@media (max-width: 1199px) {
  .caption h3 {
    font-size: 16px;
  }
}
</style>