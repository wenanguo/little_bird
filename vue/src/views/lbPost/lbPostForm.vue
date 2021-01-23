<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :maskClosable="false"
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
                      <a-input v-decorator="['title', {rules: [{required: true, min: 1, message: '请输入文章标题！'}]}]"/>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="描述">
                      <a-input v-decorator="['description', {rules: [{required: true, min: 1, message: '请输入文章描述！'}]}]"/>
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
                  <a-col :span="12">
                    <a-form-item label="排序">
                      <a-input-number style="width:100%" v-decorator="['postOrder', {initialValue: 1,rules: [{required: true, message: '请输入排序！'}]}]" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12" v-if="isSg">
                    <a-form-item label="引用标题">
                      <a-input v-decorator="['quoteTitle', {rules: [{required: false}]}]"/>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12" v-if="isSg" >
                    <a-form-item label="引用简介">
                      <a-textarea v-decorator="['quoteDesc', {rules: [{required: false}]}]"/>
                    </a-form-item>
                  </a-col>
                  <!-- <a-col :span="12">
                    <a-form-item label="广告链接地址">
                      <a-input v-decorator="['linkUrl', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                  </a-col> -->
                  <a-col :span="12">
                    <a-form-item label="主题信息">
                      <a-textarea v-decorator="['themeInfo', {rules: [{required: false, message: '请输入主题信息！'}]}]" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="作者">
                      <a-select mode="multiple" v-decorator="['lbAuthorIdsList', {rules: [{required: true, message: '请选择作者！'}]}]" placeholder="请选择作者">
                        <a-select-option v-for="lbAuthor in this.lbAuthorList" :key="lbAuthor.id" :value="lbAuthor.id">
                          {{ lbAuthor.name }}
                        </a-select-option>
                      </a-select>
                      <!-- <a-input v-decorator="['author', {rules: [{required: true, message: '请输入作者名称！'}]}]" /> -->
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="发布时间">
                      <a-date-picker show-time style="width: 100%" v-decorator="['publishedAt', {rules: [{required: true, message: '请输入发布时间！'}]}]">
                      </a-date-picker>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="是否推荐">
                      <a-radio-group name="radioGroup" v-decorator="['recommend', {initialValue: 1,rules: [{required: true}]}]">
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
                    <a-form-item label="是否预览">
                      <a-radio-group name="radioGroup" v-decorator="['isPreview', {initialValue: 1,rules: [{required: true}]}]">
                        <a-radio :value="1">
                          正常
                        </a-radio>
                        <a-radio :value="2">
                          预览
                        </a-radio>
                      </a-radio-group>
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="是否免费">
                      <a-radio-group name="radioGroup" v-decorator="['isFree', {initialValue: 1,rules: [{required: true}]}]">
                        <a-radio :value="1">
                          免费
                        </a-radio>
                        <a-radio :value="2">
                          收费
                        </a-radio>
                        <a-radio :value="3">
                          测试
                        </a-radio>
                      </a-radio-group>
                    </a-form-item>
                  </a-col>
                  <a-col :span="24">
                    <a-col :span="12">
                      <a-form-item label="状态">
                        <a-radio-group v-decorator="['status', { initialValue: 100 }]">
                          <a-radio :value="100">正常</a-radio>
                          <a-radio :value="101">禁用</a-radio>
                        </a-radio-group>
                      </a-form-item>
                    </a-col>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="题图" help="左右图文：450*780，上下图文：1005*540">
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
                      <a-input v-decorator="['imgUrl', {initialValue: '',rules: [{required: true, message: '请上传题图！'}]}]" type="hidden" />
                      <a-input v-decorator="['content', {initialValue: ''}]" type="hidden" />
                      <a-input v-decorator="['feeContent', {initialValue: ''}]" type="hidden" />
                    </a-form-item>
                  </a-col>
                  <a-col :span="12">
                    <a-form-item label="长题图" help="图片大小：1125*1350">
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
                      <a-input v-decorator="['preimgUrl', {initialValue: '',rules: [{required: true, message: '请上传长题图！'}]}]" type="hidden" />
                    </a-form-item>
                  </a-col>
                </a-row>
              </a-tab-pane>
              <a-tab-pane key="2" tab="免费内容" force-render>
                <div class="editBox">
                  <a-upload
                    class="contentImg"
                    :multiple="false"
                    list-type="picture"
                    :file-list="contentfileList"
                    @change="contenthandleChange"
                    action="/api/tencent/upload"
                  >
                    <a-button> <a-icon type="upload" />上传图片</a-button>
                  </a-upload>
                  <quill-editor v-model="content" ref="contentQuillEditor" :options="contentEditorOption"></quill-editor>
                </div>
              </a-tab-pane>
              <a-tab-pane key="3" tab="收费内容" force-render>
                <div class="editBox">
                  <a-upload
                    class="feeContentImg"
                    :multiple="false"
                    list-type="picture"
                    :file-list="feeContentfileList"
                    @change="feeContenthandleChange"
                    action="/api/tencent/upload"
                  >
                    <a-button> <a-icon type="upload" />上传图片</a-button>
                  </a-upload>
                  <quill-editor v-model="feeContent" ref="feeContentQuillEditor" :options="feeContentEditorOption"></quill-editor>
                </div>
              </a-tab-pane>
            </a-tabs>
          </a-col>
          <a-col :span="8">
            <div class="phone-view">
              <div class="ql-container ql-snow">
                <div class="ql-editor">
                  <div class="article-editor">
                    <div class="articleClass" ><img style="width:100%" :src="articleClass"></div>
                    <div class="articleTitle" v-html="articleTitle" ></div>
                    <div class="articleAuthor" v-html="articleAuthor"><span ></span> | <span ></span></div>
                    <div class="articleDate" v-html="articleDate"></div>
                    <div class="articleDescription" v-html="articleDescription"></div>
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
  import { listGetVal, getSocialDateDisplay } from '@/utils/util'
  // 表单字段
  const fields = [
    'id',
    'periodicalId',
    'title',
    'content',
    'feeContent',
    'shareTitle',
    'shareContent',
    'quoteTitle',
    'quoteDesc',
    'description',
    'postSubjectId',
    'postCatalogId',
    'isFree',
    'isPreview',
    'showType',
    'imgUrl',
    'preimgUrl',
    'themeInfo',
    'postOrder',
    'publishedAt',
    'recommend',
    'lbAuthorIdsList',
    'status'
  ]
  const quillContainer = [
                ['bold', 'italic', 'underline', 'strike'],
                ['code-block'],
                [{ 'list': 'bullet' }],
                [{ 'header': 1 }, { 'header': 2 }],
                [{ 'indent': '-1' }, { 'indent': '+1' }],
                [{ 'align': [] }],
                ['link', 'image'],
                [{ 'list': 'ordered' }],
                ['clean']
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
        isSg: false,
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
        contentfileList: [],
        feeContentfileList: [],
        contentEditorOption: {
          placeholder: '输入文章内容',
          modules: {
            toolbar: {
              container: quillContainer,
              handlers: {
                'image': (value) => {
                      if (value) {
                          document.querySelector('.contentImg input').click()
                      } else {
                          this.quill.format('image', false)
                      }
                    }
              }
            }
          }
        },
        feeContentEditorOption: {
          placeholder: '输入文章内容',
          modules: {
            toolbar: {
              container: quillContainer,
              handlers: {
                'image': (value) => {
                      if (value) {
                          document.querySelector('.feeContentImg input').click()
                      } else {
                          this.quill.format('image', false)
                      }
                    }
              }
            }
          }
        }
      }
    },
    created () {
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
        // 初始化
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
          this.content = this.model.content
          this.feeContent = this.model.feeContent
          this.articleClass = this.model.preimgUrl
          this.model && this.form.setFieldsValue(pick(this.model, fields))
        } else {
          this.fileList = null
          this.prefileList = null
          this.content = ''
          this.feeContent = ''
          this.articleTitle = ''
          this.articleAuthor = ''
          this.articleDescription = ''
          this.articleDate = ''
          this.articleClass = ''
        }
      })
    },
    methods: {
      gchange (props, values) {
        for (var p in values) {
          if (p === 'title') {
            this.articleTitle = values[p]
          } else if (p === 'lbAuthorIdsList') {
            var authorstr = ''
            for (var j = 0; j < values[p].length; j++) {
              authorstr = authorstr + '&nbsp;' + listGetVal(this.lbAuthorList, values[p][j], 'id', 'name')
            }
            this.articleAuthor = authorstr
          } else if (p === 'publishedAt') {
            this.articleDate = getSocialDateDisplay(values[p])
          } else if (p === 'description') {
            this.articleDescription = values[p]
          } else if (p === 'postCatalogId') {
            if (values[p] === 1) {
               this.isSg = true
            } else {
              this.isSg = false
            }
          }
        }
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
          this.articleClass = info.file.response.result.url
        }
      },
      feeContenthandleChange (info) {
        const feeContentfileListt = [...info.fileList]

        this.feeContentfileList = feeContentfileListt.slice(-1)

        if (info.file.status === 'uploading') {
          this.$emit('update:loading', true)
          return
        }
        if (info.file.status === 'done') {
          this.$emit('update:loading', false)

          const quill = this.$refs.feeContentQuillEditor.quill
          const length = quill.getSelection().index
          quill.insertEmbed(length, 'image', info.file.response.result.url)
          quill.setSelection(length + 1)
        }
      },
      contenthandleChange (info) {
        const contentfileListt = [...info.fileList]

        this.contentfileList = contentfileListt.slice(-1)

        if (info.file.status === 'uploading') {
          this.$emit('update:loading', true)
          return
        }
        if (info.file.status === 'done') {
          this.$emit('update:loading', false)

          const quill = this.$refs.contentQuillEditor.quill
          const length = quill.getSelection().index
          quill.insertEmbed(length, 'image', info.file.response.result.url)
          quill.setSelection(length + 1)
        }
      }
    }
  }
</script>
<style>
.contentImg,
.feeContentImg{
    width: 0;
    height: 0;
    display: none;
}
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
  .ql-blockquote svg,.ql-code-block svg,.ql-list svg{
    display: none;
  }
  .ql-code-block{
    background:url("../../assets/summary_icon.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-snow .ql-formats:nth-child(3) .ql-list{
    background:url("../../assets/image-text.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-snow .ql-formats:nth-child(8) .ql-list{
    background:url("../../assets/album.png") no-repeat center !important;
    background-size: 70% 70% !important;
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
  .ql-editor ul{
    list-style-type: none!important;
    padding:10px 17px 15px 17px;
    background:url('../../assets/article-line.png') no-repeat right+10px top rgba(229, 230, 231, 0.2);
  }
  .ql-editor ul li{
    color: #767676!important;
    font-size: 12px!important;
    font-weight: 300!important;
    line-height: 17px!important;
    text-align: right;
    margin: 0 !important;
    padding: 0 !important;
    list-style-type: none!important;
  }
  .ql-editor ul li::before{
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
  .ql-editor p,.article-editor{
    padding: 10px;
    font-size: 14px;
  }
  /*.ant-modal{*/
  /*  width: 100% !important;*/
  /*}*/
  .ql-editing{
    left:auto !important;
    /* right: 10px !important; */
    /*top: 0!important;*/
    width: 320px;
    -webkit-transform: translateY(-30px);
    transform: translateY(-30px);
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
  .ql-editor .ql-indent-1:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 3em;
  }
  .ql-editor .ql-indent-2:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 6em;
  }
  .ql-editor .ql-indent-3:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 9em;
  }
  .ql-editor .ql-indent-4:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 12em;
  }
  .ql-editor .ql-indent-5:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 15em;
  }
  .ql-editor .ql-indent-6:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 18em;
  }
  .ql-editor .ql-indent-7:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 21em;
  }
  .ql-editor .ql-indent-6:not(.ql-direction-rtl) {
    padding-left: 0!important;
    text-indent: 24em;
  }
  .ql-snow a {
    color: #5F5E6C!important;
    border-bottom: #D7A03C solid 1px;
    text-decoration: none!important;
  }
  .ql-toolbar.ql-snow .ql-formats{
    margin-right: 0!important;
  }
  .ql-action{
    border-bottom:none!important;
  }
  .ql-action::after{
    color: #06c!important;
  }
</style>
