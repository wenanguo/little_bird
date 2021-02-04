<template>
  <a-modal
    :title="title"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="false" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="标题">
          <a-input v-decorator="['title', {rules: [{required: true}]}]" disabled />
          <a-input v-decorator="['content', {initialValue: ''}]" type="hidden" />
        </a-form-item>
        <div class="editBox">
          <quill-editor v-model="content" ref="contentQuillEditor" :options="contentEditorOption"></quill-editor>
        </div>
      </a-form>
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
        'content'
    ]
    const quillContainer = [
                ['bold', 'italic', 'underline', 'strike'],
                ['code-block'],
                [{ 'list': 'bullet' }],
                [{ 'header': 1 }, { 'header': 2 }],
                [{ 'indent': '-1' }, { 'indent': '+1' }],
                [{ 'align': [] }],
                [{ 'list': 'ordered' }],
                ['clean']
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
                contentEditorOption: {
                      placeholder: '输入文章内容',
                      modules: {
                        toolbar: {
                          container: quillContainer
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
            this.content && this.form.setFieldsValue({ content: this.content })
          })

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
              if (this.model) {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
                this.content = this.model.content
              } else {
                this.content = ''
              }
            })
        }
    }
</script>
<style>
.editBox .ql-editor{
    height:420px;
  }
</style>
