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
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="期刊号">
          <a-input v-decorator="['title', {rules: [{required: true, min: 1, message: '请输入期刊号！'}]}]" />
        </a-form-item>
        <a-form-item label="期刊编号">
          <a-input v-decorator="['tcode', {rules: [{required: true, min: 1, message: '请输入期刊编号！'}]}]" />
        </a-form-item>
        <!-- <a-form-item label="期刊推荐">
          <a-radio-group v-decorator="['recommend', { initialValue: '2' }]">
            <a-radio :value="1">是</a-radio>
            <a-radio :value="2">否</a-radio>
          </a-radio-group>
        </a-form-item> -->
        <a-form-item label="所属年份">
          <a-input v-decorator="['tyear', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="排序">
          <a-input-number style="width: 100%" v-decorator="['torder', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>

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

        <a-form-item label="期刊封面" help="图片大小：1125*1500">
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
        <a-form-item label="PDF">
          <a-upload
            name="file"
            :multiple="false"
            list-type="picture"
            :file-list="pdffileList"
            @change="pdfhandleChange"
            action="/api/tencent/upload"
          >
            <a-button> <a-icon type="upload" />上传PDF</a-button>
          </a-upload>
          <a-input v-decorator="['tpdf', {initialValue: ''}]" type="hidden" />
          <a-input v-decorator="['tinfo', {initialValue: ''}]" type="hidden" />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
      <div class="editBox">
        <quill-editor v-model="content" ref="myQuillEditor" :options="editorOption"></quill-editor>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { quillEditor } from 'vue-quill-editor'
    // 表单字段
    const fields = [
        'id',
        'title',
        'tcode',
        'imgUrl',
        'tinfo',
        'tpdf',
        'isPreview',
        'recommend',
        'tyear',
        'torder',
        'status'
    ]

    export default {
      components: { quillEditor },
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
                content: '',
                fileList: [],
                pdffileList: [],
                editorOption: {
                placeholder: '输入期刊描述',
                modules: {
                  toolbar: {
                    container: [
                      ['bold']// ['bold', 'italic', 'underline', 'strike']
                      // ['code-block', 'blockquote'],
                      // [{ 'header': 1 }, { 'header': 2 }],
                      // [{ 'script': 'sub' }],
                      // [{ 'indent': '-1' }, { 'indent': '+1' }],
                      // [{ 'direction': 'rtl' }],
                      // [{ 'size': ['small', false, 'large', 'huge'] }],
                      // [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                      // [{ 'color': [] }, { 'background': [] }],
                      // [{ 'font': [] }],
                      // [{ 'align': [] }],
                      // ['clean'],
                      // ['link', 'image'],
                      // [{ 'list': 'ordered' }]
                    ]
                  }
                }
              }
            }
        },
        created () {
            // 当 富文本内容 发生改变时，为表单设置值
          this.$watch('content', () => {
            console.log(this.content)
            this.content && this.form.setFieldsValue({ tinfo: this.content })
          })

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
                  this.pdffileList = [{
                                    uid: '-1',
                                    name: this.model.tpdf,
                                    status: 'done',
                                    url: this.model.tpdf
                                  }]
                  this.content = this.model.tinfo
                } else {
                  this.fileList = []
                  this.pdffileList = []
                  this.content = ''
                }
            })
        },
        methods: {
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
          pdfhandleChange (info) {
            const fileListt = [...info.fileList]

            this.pdffileList = fileListt.slice(-1)

            if (info.file.status === 'uploading') {
              this.$emit('update:loading', true)
              return
            }
            if (info.file.status === 'done') {
              this.$emit('update:loading', false)
              this.form.setFieldsValue({ tpdf: info.file.response.result.url })
            }
          }
        }

    }
</script>
<style>
  .editBox .ql-editor{
    height:200px;
  }
  .editBox p{
    font-size: 14px;
  }
</style>
