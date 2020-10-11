<template>
    <div>
        <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-upload"></i>
            {{text}}
        </button>
        <input class="hidden"  type="file" ref="file" v-bind:id="inputId+'-input'"  v-on:change="uplodFile()">
    </div>
</template>

<script>
  export default {
      name: 'file',
      props: {
          text:{
              default:"上传文件"
          },
          inputId:{
              default: 'file-upload'
          },
          use:{
              default:""
          },
          suffixs:{
              default:['jpg','png','jpeg']
          },
          afterUpload:{
              type:Function,
              default: null
          }
      },
      data: function () {
          return {
              total: 0, // 总行数
              size: 10, // 每页条数
              page: 0, // 当前页码
              pageTotal: 0, // 总页数
              pages: [], // 显示的页码数组
          }
      },
      methods: {
      /**
       * 上传文件
       */
      uplodFile(){
          let _this = this;
          let formData = new window.FormData();
          /**
           * .$refs vue 别名
           */
          let file = _this.$refs.file.files[0];
          //判断文件格式
          let suffixs = _this.suffixs;
          let fileName = file.name;
          let suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
          let validateSuffix = false;
          for (let i = 0; i < suffixs.length ; i++) {
              if (suffixs[i].toLowerCase() == suffix ){
                  validateSuffix = true;
                  break;
              }
          }
          if (!validateSuffix){
              Toast.warning("文件格式不正确！支支持上传："+suffixs.join(","));
              return;
          }
          formData.append('file',file);
          formData.append('use',_this.use);
          Loading.show();
          _this.$axios.post(process.env.VUE_APP_SERVER + '/file/admin/upload',formData).then((response)=>{
              Loading.hide();
              let resp = response.data;
              console.log("文件地址："+resp.content)
              _this.afterUpload(resp);
              $("#"+_this.inputId+"-input").val("");

          })
      },
      selectFile(){
          let _this = this;
          $("#"+_this.inputId+"-input").trigger("click");
      }
      }
  }
  </script>