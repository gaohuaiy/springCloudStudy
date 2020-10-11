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
      name: 'big-file',
      props: {
          text:{
              default:"上传大文件"
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

          //文件分片
          let shardSize  = 20*1024*1024; //以20MB为一个分片
          let shardIndex = 1;//分片索引
          let start = shardIndex*shardSize;//当前分片起始位置
          let end = Math.min(file.size,start+shardSize);//当前分片结束位置
          let fileShard = file.slice(start,end);//从文件中截取当前的分片数据
          let size = file.size;
          let shardTotal = Math.ceil(size/shardSize);
          let key = hex_md5(file);
          let key10 = parseInt(key);
          let key62 = Tool._10to62(key10)//节约空姐
          formData.append('shard',fileShard);
          formData.append('shardIndex',shardIndex);
          formData.append('shardSize',shardSize);
          formData.append('shardTotal',shardTotal);
          formData.append('use',_this.use);
          formData.append('suffix',suffix);
          formData.append('name',file.name);
          formData.append('size',size);
          formData.append('key',key62);
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