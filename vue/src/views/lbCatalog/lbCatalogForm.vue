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
        <a-form-item v-show="model && model.id > 0" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="标题">
          <a-input v-decorator="['title', {rules: [{required: true, min: 1, message: '请输入内容！'}]}]" />
        </a-form-item>
        <a-form-item label="排序">
          <a-input-number v-decorator="['torder', {rules: [{required: true, message: '请输入排序！'}]}]" />
        </a-form-item>
        <a-form-item label="分割线颜色">
          <colorPicker v-decorator="['tcolor', { initialValue: '#777777' }]" @change="handleColorChange"/>
          <span>
            {{ this.color }}
          </span>
        </a-form-item>
        <a-form-item label="分类">
          <a-radio-group v-decorator="['ttype', { initialValue: 1 }]">
            <a-radio :value="1">半屏</a-radio>
            <a-radio :value="2">全屏</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="推荐">
          <a-radio-group v-decorator="['recommend', { initialValue: 2 }]">
            <a-radio :value="1">推荐</a-radio>
            <a-radio :value="2">不推荐</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import vcolorpicker from 'vcolorpicker'
    // 表单字段
    const fields = [
        'id',
        'title',
        'tcolor',
        'torder',
        'status',
        'ttype',
        'recommend',
        'updateTime',
        'createTime'
    ]

    export default {
        components: { vcolorpicker },
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
                color: '#eeeeee',
                form: this.$form.createForm(this)
            }
        },
        created () {
            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                var json = pick(this.model, fields)
                this.model && this.form.setFieldsValue(json)
                this.color = this.model.color
            })
        },
        methods: {
          handleColorChange (info) {
            this.color = info
          }
        }
    }
</script>
