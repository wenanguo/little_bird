<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >

    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">

        <a-row :span="24" :gutter="24">
          <a-tabs default-active-key="1" >
            <a-tab-pane key="1" tab="属性">
              <a-col :span="0">
                <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
                <a-form-item v-show="model && model.id > 0" label="编号">
                  <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="期刊id">
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
                  <a-input v-decorator="['title', {rules: [{required: true, min: 1, message: '请输入名章标题！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="描述">
                  <a-input v-decorator="['description', {rules: [{required: true, min: 1, message: '请输入文章摘要！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="栏目">
                  <!-- <a-input v-decorator="['postSubjectId', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" /> -->
                  <a-select v-decorator="['postSubjectId', {rules: [{required: true, message: '请选择所属分类！'}]}]">
                    <a-select-option v-for="lbSubject in this.lbSubjectList" :key="lbSubject.id">
                      {{ lbSubject.title }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="所属分类">
                  <!-- <a-input v-decorator="['postCatalogId', {rules: [{required: true,  message: '请输入至少五个字符的规则描述！'}]}]" /> -->
                  <a-select v-decorator="['postCatalogId', {rules: [{required: true, message: '请选择所属分类！'}]}]">
                    <a-select-option v-for="lbCatalog in this.lbCatalogList" :key="lbCatalog.id">
                      {{ lbCatalog.title }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="分类颜色">
                  <a-input v-decorator="['tcolor', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="显示样式">
                  <a-select v-decorator="['showType', {rules: [{required: true, message: '请选择分类！'}]}]">
                    <a-select-option value="0">左右图文</a-select-option>
                    <a-select-option value="1">上下图文</a-select-option>
                    <a-select-option value="2">广告类型</a-select-option>
                    <a-select-option value="3">无图</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="广告链接地址">
                  <a-input v-decorator="['linkUrl', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="主题信息">
                  <a-input v-decorator="['themeInfo', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="作者">
                  <!-- <a-select show-search mode="multiple" v-decorator="['author', {rules: [{required: true, message: '请选择作者！'}]}]" placeholder="请选择作者">
                    <a-select-option value="jack">
                      Jack
                    </a-select-option>
                    <a-select-option value="lucy">
                      Lucy
                    </a-select-option>
                    <a-select-option value="tom">
                      Tom
                    </a-select-option>
                  </a-select> -->
                  <a-input v-decorator="['author', {rules: [{required: true, message: '请输入作者名称！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="排序">
                  <a-input-number v-decorator="['postOrder', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="发布时间">
                  <a-date-picker style="width: 100%" show-time v-decorator="['publishedAt', {rules: [{required: true}]}]" >
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
                <a-form-item label="图片上传">
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
                </a-form-item>
              </a-col>
            </a-tab-pane>
            <a-tab-pane key="2" tab="内容" force-render>
              <a-col :span="24">
                <div class="editBox">
                  <quill-editor v-model="content" ref="myQuillEditor" :options="editorOption"></quill-editor>
                </div>
              </a-col>
            </a-tab-pane>
          </a-tabs>

        </a-row>
        <div class="phone-view">
          <div class="ql-container ql-snow">
            <div class="ql-editor" v-html="this.content"></div>
          </div>
        </div>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { quillEditor } from 'vue-quill-editor'
    import quillConfig from './quill-config.js'
    import AFormItem from 'ant-design-vue/es/form/FormItem'
    import ACol from 'ant-design-vue/es/grid/Col'
    // 表单字段
    const fields = [
        'id',
        'periodicalId',
        'title',
        'description',
        'postSubjectId',
        'postSubject',
        'postCatalog',
        'postCatalogId',
        'tcolor',
        'showType',
        'imgUrl',
        'linkUrl',
        'themeInfo',
        'author',
        'postOrder',
        'publishedAt',
        'praiseCount',
        'recordCount',
        'readCount',
        'recommend',
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
                form: this.$form.createForm(this),
                content: null,
                fileList: [],
                editorOption: {
                  modules: {
                    toolbar: {
                      container: [
                        ['bold', 'italic', 'underline', 'strike'],
                        ['blockquote', 'code-block'],
                        [{ 'header': 1 }, { 'header': 2 }],
                        // [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                        // [{ 'script': 'sub' }, { 'script': 'super' }],
                        [{ 'indent': '-1' }, { 'indent': '+1' }],
                        // [{ 'direction': 'rtl' }],
                        // [{ 'size': ['small', false, 'large', 'huge'] }],
                        // [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                        [{ 'color': [] }, { 'background': [] }],
                        // [{ 'font': [] }],
                        [{ 'align': [] }],
                        // ['clean'],
                        ['link', 'image', 'video']
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

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))

                 // 初始化图片上传
                if (this.model) {
                  this.fileList = [{
                                    uid: '-1',
                                    name: this.model.imgUrl,
                                    status: 'done',
                                    url: this.model.imgUrl
                                  }]
                } else {
                  this.fileList = []
                }
            })
        },
        mounted () {
          quillConfig.initButton()
        },
        methods: {
          handleChange (info) {
            const fileListt = [...info.fileList]

            this.fileList = fileListt.slice(-1)

            if (info.file.status === 'uploading') {
              return
            }
            if (info.file.status === 'done') {
              // Get this url from response in real world.
              console.log(info.file.response.result.url)
              this.form.setFieldsValue({ imgUrl: info.file.response.result.url })
            }
          }
        }
    }
</script>
<style>
  .editBox .ql-editor{
    height:20vw;
  }
  .phone-view{
    background: url("../../assets/phone-bg.png") no-repeat;
    background-size: 100%;
    width: 20vw;
    height: 40vw;
    margin-top: -20vw;
    padding: 5.5vw 0;
    position: fixed;
    top: 50%;
    right: 20px;
  }
  .phone-view .ql-container.ql-snow{
    border:0;
  }
  .phone-view .ql-editor{
    width:14.5vw;
    margin: 0 auto;
    height:25.5vw;
  }
  .ql-blockquote{
    background:url("../../assets/image-text.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-blockquote svg,.ql-code-block svg{
    display: none;
  }
  .ql-code-block{
    background:url("../../assets/summary_icon.png") no-repeat center !important;
    background-size: 70% 70% !important;
  }
  .ql-editor blockquote{
    background: rgba(229, 230, 231, 0.2);
    border: 0!important;
    margin: 0!important;
    padding:0 10px 0 10px;
  }
  .ql-snow .ql-editor pre.ql-syntax{
    background:url('../../assets/summary_icon.png') no-repeat 20px 20px rgba(229, 230, 231, 0.2);
    background-size: 10%;
    color: #3F3E4C;
    border: 0!important;
    margin: 0!important;
    padding:10px 10px 10px 15%;
    border-radius: 0;
    font-size:1.2em;
    text-align: right;
  }
  /*.ant-modal{*/
  /*  width: 100% !important;*/
  /*}*/
  .ql-editing{
    left: 50% !important;
    top: 0!important;
  }
</style>
