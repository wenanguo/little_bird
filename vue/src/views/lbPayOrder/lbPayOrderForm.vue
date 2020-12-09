<template>
  <a-modal
    :title="title"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="商品标题">
          <a-input v-decorator="['subject', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="商品描述">
          <a-input v-decorator="['body', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="金额">
          <a-input v-decorator="['totalAmount', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="创建订单相应">
          <a-input v-decorator="['tradeAppPayResponse', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="支付宝交易号">
          <a-input v-decorator="['tradeNo', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="商户订单号">
          <a-input v-decorator="['outTradeNo', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['状态', { initialValue: 100 }]" disabled >
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="创建时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['createTime', {rules: [{required: true}]}]" disabled >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="更新时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['updateTime', {rules: [{required: true}]}]" disabled >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="收款支付宝账号对应的支付宝唯一用户号">
          <a-input v-decorator="['sellerId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="收款支付宝账号">
          <a-input v-decorator="['sellerEmail', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="商户原始订单号">
          <a-input v-decorator="['merchantOrderNo', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="买家支付宝用户号">
          <a-input v-decorator="['buyerId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="买家支付宝账号">
          <a-input v-decorator="['buyerLogonId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="交易状态">
          <a-input v-decorator="['tradeStatus', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="实收金额">
          <a-input v-decorator="['receiptAmount', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="开票金额">
          <a-input v-decorator="['invoiceAmount', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="付款金额">
          <a-input v-decorator="['buyerPayAmount', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="集分宝金额">
          <a-input v-decorator="['pointAmount', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="总退款金额">
          <a-input v-decorator="['refundFee', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="交易创建时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['gmtCreate', {rules: [{required: true}]}]" disabled >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="交易付款时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['gmtPayment', {rules: [{required: true}]}]" disabled >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="交易退款时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['gmtRefund', {rules: [{required: true}]}]" disabled >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="交易结束时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['gmtClose', {rules: [{required: true}]}]" disabled >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="支付金额信息">
          <a-input v-decorator="['fundBillList', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="回传参数">
          <a-input v-decorator="['passbackParams', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="优惠券信息">
          <a-input v-decorator="['voucherDetailList', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
        </a-form-item>
        <a-form-item label="异步通知相应">
          <a-input v-decorator="['notifyResponse', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" disabled />
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
        'subject',
        'body',
        'totalAmount',
        'tradeAppPayResponse',
        'tradeNo',
        'outTradeNo',
        'status',
        'createTime',
        'updateTime',
        'sellerId',
        'sellerEmail',
        'merchantOrderNo',
        'buyerId',
        'buyerLogonId',
        'tradeStatus',
        'receiptAmount',
        'invoiceAmount',
        'buyerPayAmount',
        'pointAmount',
        'refundFee',
        'gmtCreate',
        'gmtPayment',
        'gmtRefund',
        'gmtClose',
        'fundBillList',
        'passbackParams',
        'voucherDetailList',
        'notifyResponse'
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
