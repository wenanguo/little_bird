<template>
  <a-modal
    :title="title"
    :width="900"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
    centered
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <a-row>
          <a-col :span="16">
            <a-tabs default-active-key="1" >
              <a-tab-pane key="1" tab="属性">
                <a-row :span="24" :gutter="10">
                  <a-col :span="0">
                    <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
                    <a-form-item v-show="model && model.id > 0" label="编号">
                      <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="所属期刊">
                      <!-- <a-input v-decorator="['periodicalId', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" /> -->
                      <a-select v-decorator="['periodicalId', {rules: [{required: true, message: '请选择所属分类！'}]}]">
                        <a-select-option v-for="lbPeriodical in this.lbPeriodicalList" :key="lbPeriodical.id">
                          {{ lbPeriodical.title }}
                        </a-select-option>
                      </a-select>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="标题">
                      <a-input v-decorator="['title', {rules: [{required: true, min: 1, message: '请输入名章标题！'}]}]"/>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="描述">
                      <a-input v-decorator="['description', {rules: [{required: true, min: 1, message: '请输入文章摘要！'}]}]"/>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="分享标题">
                      <a-input v-decorator="['shareTitle', {rules: [{required: false}]}]" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="分享内容">
                      <a-input v-decorator="['shareContent', {rules: [{required: false}]}]" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="栏目">
                      <!-- <a-input v-decorator="['postSubjectId', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" /> -->
                      <a-select v-decorator="['postSubjectId', {rules: [{required: true, message: '请选择所属栏目！'}]}]">
                        <a-select-option v-for="lbSubject in this.lbSubjectList" :key="lbSubject.id" :value="lbSubject.id">
                          {{ lbSubject.title }}
                        </a-select-option>
                      </a-select>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="所属分类">
                      <!-- <a-input v-decorator="['postCatalogId', {rules: [{required: true,  message: '请输入至少五个字符的规则描述！'}]}]" /> -->
                      <a-select v-decorator="['postCatalogId', {rules: [{required: true, message: '请选择所属分类！'}]}]">
                        <a-select-option v-for="lbCatalog in this.lbCatalogList" :key="lbCatalog.id" :value="lbCatalog.id">
                          {{ lbCatalog.title }}
                        </a-select-option>
                      </a-select>
                    </a-form-item>
                  </a-col>
                  <!-- <a-col :span="12">
                    <a-form-item label="分类颜色">
                      <a-input v-decorator="['tcolor', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                  </a-col> -->
                  <a-col :span="12">
                    <a-form-item label="显示样式">
                      <a-select v-decorator="['showType', {rules: [{required: true, message: '请选择分类！'}]}]">
                        <a-select-option :value="1">左右图文</a-select-option>
                        <a-select-option :value="2">上下图文</a-select-option>
                        <a-select-option :value="4">无图</a-select-option>
                      </a-select>
                    </a-form-item>
                  </a-col>
                  <!-- <a-col :span="12">
                    <a-form-item label="广告链接地址">
                      <a-input v-decorator="['linkUrl', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                  </a-col> -->
                  <a-col :span="12">
                    <a-form-item label="主题信息">
                      <a-input v-decorator="['themeInfo', {rules: [{required: true, message: '请输入至少五个字符的主题信息！'}]}]" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="作者">
                      <a-select mode="multiple" @change="handleAuthorChange" v-decorator="['lbAuthorIdsList', {rules: [{required: true, message: '请选择作者！'}]}]" placeholder="请选择作者">
                        <a-select-option v-for="lbAuthor in this.lbAuthorList" :key="lbAuthor.id" :value="lbAuthor.id">
                          {{ lbAuthor.name }}
                        </a-select-option>
                      </a-select>
                      <!-- <a-input v-decorator="['author', {rules: [{required: true, message: '请输入作者名称！'}]}]" /> -->
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="排序">
                      <a-input-number v-decorator="['postOrder', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="发布时间">
                      <a-date-picker style="width: 100%" v-decorator="['publishedAt', {rules: [{required: true}]}]">
                      </a-date-picker>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="是否推荐">
                      <a-radio-group name="radioGroup" :default-value="1">
                        <a-radio :value="1">
                          是
                        </a-radio>
                        <a-radio :value="2">
                          否
                        </a-radio>
                      </a-radio-group>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="状态">
                      <a-radio-group v-decorator="['status', { initialValue: 100 }]">
                        <a-radio :value="100">正常</a-radio>
                        <a-radio :value="101">禁用</a-radio>
                      </a-radio-group>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="题图">
                      <a-upload
                        name="file"
                        :multiple="false"
                        list-type="picture"
                        :file-list="fileList"
                        @change="handleChange"
                        action="/api/tencent/upload"
                      >
                        <a-button> <a-icon type="upload" />上传图片</a-button>
                      </a-upload>
                      <a-input v-decorator="['imgUrl', {initialValue: ''}]" type="hidden" />
                      <a-input v-decorator="['preimgUrl', {initialValue: ''}]" type="hidden" />
                      <a-input v-decorator="['content', {initialValue: ''}]" type="hidden" />
                      <a-input v-decorator="['feeContent', {initialValue: ''}]" type="hidden" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="长题图">
                      <a-upload
                        name="file"
                        :multiple="false"
                        list-type="picture"
                        :file-list="prefileList"
                        @change="prehandleChange"
                        action="/api/tencent/upload"
                      >
                        <a-button> <a-icon type="upload" />上传图片</a-button>
                      </a-upload>
                    </a-form-item>
                  </a-col>
                </a-row>
              </a-tab-pane>
              <a-tab-pane key="2" tab="免费内容" force-render>
                <div class="editBox">
                  <quill-editor v-model="content" ref="myQuillEditor" :options="editorOption"></quill-editor>
                </div>
              </a-tab-pane>
              <a-tab-pane key="3" tab="收费内容" force-render>
                <div class="editBox">
                  <quill-editor v-model="feeContent" ref="myQuillEditor" :options="editorOption"></quill-editor>
                </div>
              </a-tab-pane>
            </a-tabs>
          </a-col>
          <a-col :span="8">
            <div class="phone-view">
              <div class="ql-container ql-snow">
                <div class="ql-editor">
                  <div class="article-editor">
                    <div class="articleClass" ></div>
                    <div class="articleTitle" ></div>
                    <div class="articleAuthor" v-html="articleAuthor"><span ></span> | <span ></span></div>
                    <div class="articleDate" ></div>
                    <div class="articleDescription" ></div>
                  </div>
                  <div v-html="this.content"></div>
                  <div v-html="this.feeContent"></div>
                </div>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import { quillEditor } from 'vue-quill-editor'
  import AFormItem from 'ant-design-vue/es/form/FormItem'
  import ACol from 'ant-design-vue/es/grid/Col'
  // 表单字段
  const fields = [
    'id',
    'periodicalId',
    'title',
    'shareTitle',
    'shareContent',
    'description',
    'postSubjectId',
    'postSubject',
    'postCatalog',
    'postCatalogId',
    'tcolor',
    'showType',
    'imgUrl',
    'preimgUrl',
    'linkUrl',
    'themeInfo',
    'author',
    'postOrder',
    'publishedAt',
    'praiseCount',
    'recordCount',
    'readCount',
    'recommend',
    'lbAuthorIdsList',
    'status',
    'updateTime',
    'createTime'
  ]
  export default {
    components: { ACol, AFormItem, quillEditor },
    props: {
      visible: {
        type: Boolean,
        required: true
      },
      title: {
        type: String,
        required: true
      },
      loading: {
        type: Boolean,
        default: () => false
      },
      model: {
        type: Object,
        default: () => null
      },
      lbCatalogList: {
        type: Array,
        default: () => null
      },
      lbAuthorList: {
        type: Array,
        default: () => null
      },
      lbSubjectList: {
        type: Array,
        default: () => null
      },
      lbPeriodicalList: {
        type: Array,
        default: () => null
      }
    },
    data () {
      this.formLayout = {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 13 }
        }
      }
      return {
        form: this.$form.createForm(this, { onValuesChange: this.gchange }),
        content: '',
        feeContent: '',
        articleTitle: '',
        articleClass: '',
        articleAuthor: '',
        articleColumn: '',
        articleDate: '',
        articleDescription: '',
        fileList: [],
        prefileList: [],
        editorOption: {
          placeholder: '输入文章收费内容',
          modules: {
            toolbar: {
              container: [
                ['bold', 'italic', 'underline', 'strike'],
                ['code-block', 'blockquote'],
                [{ 'header': 1 }, { 'header': 2 }],
                // [{ 'script': 'sub' }],
                [{ 'indent': '-1' }, { 'indent': '+1' }],
                // [{ 'direction': 'rtl' }],
                // [{ 'size': ['small', false, 'large', 'huge'] }],
                // [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                // [{ 'color': [] }, { 'background': [] }],
                // [{ 'font': [] }],
                [{ 'align': [] }],
                // ['clean'],
                ['link', 'image'],
                [{ 'list': 'ordered' }]
              ]
            }
          }
        }
      }
    },
    created () {
      console.log('custom modal created')

      // 防止表单未注册
      fields.forEach(v => this.form.getFieldDecorator(v))

      // 当 富文本内容 发生改变时，为表单设置值
      this.$watch('content', () => {
        this.feeContent && this.form.setFieldsValue({ feeContent: this.feeContent })
        this.content && this.form.setFieldsValue({ content: this.content })
      })
      this.$watch('feeContent', () => {
        this.feeContent && this.form.setFieldsValue({ feeContent: this.feeContent })
        this.content && this.form.setFieldsValue({ content: this.content })
      })

      // 当 model 发生改变时，为表单设置值
      this.$watch('model', () => {
        this.model && this.form.setFieldsValue(pick(this.model, fields))
        this.content = this.model.content
        this.feeContent = this.model.feeContent
        console.log(this.model)
        // this.form.setFieldsValue({ lbAuthorIdsList: [2, 3] })
        // 初始化图片上传
        if (this.model) {
          this.fileList = [{
            uid: '-1',
            name: this.model.imgUrl,
            status: 'done',
            url: this.model.imgUrl
          }]
          this.prefileList = [{
            uid: '-1',
            name: this.model.preimgUrl,
            status: 'done',
            url: this.model.preimgUrl
          }]
        } else {
          this.fileList = []
          this.prefileList = []
        }
      })
    },
    methods: {
      gchange (props, values) {
          console.log(props.title)
          console.log(props)
          console.log(values)
      },
      handleAuthorChange (info) {
        var authorstr = ''
        for (var j = 0; j < info.length; j++) {
          for (var i = 0; i < this.lbAuthorList.length; i++) {
              if (this.lbAuthorList[i].id === info[j]) {
                authorstr = authorstr + ' | ' + this.lbAuthorList[i].name
              }
          }
        }
        this.articleAuthor = authorstr
      },
      handleChange (info) {
        const fileListt = [...info.fileList]

        this.fileList = fileListt.slice(-1)

        if (info.file.status === 'uploading') {
          this.$emit('update:loading', true)
          return
        }
        if (info.file.status === 'done') {
          this.$emit('update:loading', false)
          this.form.setFieldsValue({ imgUrl: info.file.response.result.url })
        }
      },
      prehandleChange (info) {
        const prefileListt = [...info.fileList]

        this.prefileList = prefileListt.slice(-1)

        if (info.file.status === 'uploading') {
          this.$emit('update:loading', true)
          return
        }
        if (info.file.status === 'done') {
          this.$emit('update:loading', false)
          this.form.setFieldsValue({ preimgUrl: info.file.response.result.url })
        }
      }
    }
  }
</script>
<style>
  .editBox .ql-editor{
    height:420px;
  }
  .phone-view{
    background: url("../../assets/phone-bg.png") no-repeat;
    background-size: 100%;
    width:300px;
    height:561px;
    padding:80px 40px 91px 40px;
  }
  .phone-view .ql-container.ql-snow{
    border:0;
  }
  .phone-view .ql-editor{
    width:220px;
    height:390px;
    padding: 0!important;
  }
  .phone-view .ql-editor::-webkit-scrollbar
  {
    width:4px;
    height: 8px;
    background-color: #fefefe;
  }

  /*定义滚动条轨道 内阴影+圆角*/
  .phone-view .ql-editor::-webkit-scrollbar-track
  {
    border-radius: 10px;
    background-color:#f1f1f1;
  }

  /*定义滑块 内阴影+圆角*/
  .phone-view .ql-editor::-webkit-scrollbar-thumb
  {
    border-radius: 10px;
    background-color: #cccccc;
  }
  .ql-blockquote{
    background:url("../../assets/image-text.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-blockquote svg,.ql-code-block svg,.ql-list svg{
    display: none;
  }
  .ql-code-block{
    background:url("../../assets/summary_icon.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-list{
    background:url("../../assets/album.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-editor blockquote{
    background:url('../../assets/article-line.png') no-repeat right+10px top rgba(229, 230, 231, 0.2);
    border: 0!important;
    margin: 0!important;
    padding:5px 10px 15px 10px;
    text-align: right;
  }
  .ql-editor ol{
    list-style-type: none!important;
    padding: 0 10px;
  }
  .ql-editor ol li{
    color: #767676!important;
    font-size: 12px!important;
    font-weight: 300!important;
    line-height: 20px!important;
    text-align: right;
    margin: 0 !important;
    padding: 0 !important;
    list-style-type: none!important;
  }
  .ql-editor ol li::before{
    display: none!important;
  }
  .ql-snow .ql-editor pre.ql-syntax{
    background:url('../../assets/summary_icon.png') no-repeat 10px 10px rgba(229, 230, 231, 0.2);
    background-size: 10%;
    color: #3F3E4C;
    border: 0!important;
    margin: 0!important;
    padding:15px 10px 10px 15%;
    border-radius: 0;
    font-size:1em;
    text-align: right;
    font-weight: bold;
  }
  .ql-snow .ql-editor h1{
    font-size: 42px!important;
    line-height: 30px!important;
    margin-top: 70px!important;
    text-align: right;
    padding: 0 10px;
  }
  .ql-snow .ql-editor h2{
    font-size: 20px!important;
    line-height: 30px!important;
    margin:14px 0 30px 0!important;
    text-align: right;
    padding: 0 10px;
  }
  .phone-view .ql-editor p,.article-editor{
    padding: 10px;
  }
  /*.ant-modal{*/
  /*  width: 100% !important;*/
  /*}*/
  .ql-editing{
    left:auto !important;
    right: 10px !important;
    top: 0!important;
    width: 320px;
  }
  .ant-form-item{
    margin-bottom: 16px!important;
  }
  .articleClass{
    text-align: right;
    font-size: 12px;
    padding-bottom: 9px;
    font-weight: bold;
  }
  .articleTitle{
    text-align: right;
    font-size: 16px;
    line-height: 22px;
    color: #3F3E4C;
    font-weight: bold;
    padding-bottom: 15px;
  }
  .articleAuthor{
    font-size: 12px;
    font-weight: bold;
    text-align: right;
    margin-bottom: 5px;
  }
  .articleDate{
    font-size: 12px;
    font-weight: 300;
    text-align: right;
  }
  .articleDescription{
    background: rgba(229, 230, 231, 0.2);
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
    padding: 10px;
    text-align: right;
    margin-top: 10px;
  }
</style>
