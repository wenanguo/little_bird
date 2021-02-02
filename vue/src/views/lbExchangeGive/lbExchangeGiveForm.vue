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
        <a-form-item label="文章编号">
          <a-select v-decorator="['postId', {rules: [{required: true, message: '请选择文章！'}]}]">
            <a-select-option v-for="lbPost in this.lbPostList" :key="lbPost.id" :value="lbPost.id">
              {{ lbPost.title }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="用户手机号">
          <a-input v-decorator="['phone', {rules: [{required: true, min: 8, message: '请输入完整手机号码！'}]}]" />
        </a-form-item>
        <a-form-item label="备注">
          <a-input v-decorator="['memo', {rules: [{required: false}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'

    // 表单字段
    const fields = [
        'id',
        'tradeNo',
        'outTradeNo',
        'postId',
        'postTitle',
        'userId',
        'phone',
        'genUserName',
        'genUserId',
        'bindUserName',
        'bindUserId',
        'memo',
        'status',
        'createTime',
        'updateTime'
    ]

    export default {
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
            lbPostList: {
              type: Array,
              default: () => null
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
                form: this.$form.createForm(this)
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
            })
        }
    }
</script>
