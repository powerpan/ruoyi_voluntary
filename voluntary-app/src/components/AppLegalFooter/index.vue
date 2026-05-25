<template>
  <footer class="legal-footer" :class="[`legal-footer--${theme}`, { 'legal-footer--compact': compact }]">
    <div class="legal-brand">
      <strong>志愿活动管理系统</strong>
      <span>志愿服务组织管理平台</span>
    </div>
    <div class="legal-links">
      <span>活动发布</span>
      <span>报名管理</span>
      <span>令牌签到</span>
      <span>服务时长统计</span>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'AppLegalFooter',
  props: {
    theme: {
      type: String,
      default: 'light'
    },
    compact: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.legal-footer {
  display: grid;
  gap: 8px;
  color: var(--voluntary-text-muted, #63716d);
  font-size: 12px;
  line-height: 1.7;
  letter-spacing: 0;
  text-align: center;
}
.legal-brand,
.legal-links,
.legal-meta {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px 18px;
  flex-wrap: wrap;
}
.legal-brand strong {
  color: var(--voluntary-text, #17211f);
  font-size: 13px;
}
.legal-brand span,
.legal-links a,
.legal-links span,
.legal-meta span {
  color: inherit;
  text-decoration: none;
}
.legal-links a:hover {
  color: var(--voluntary-primary, #238276);
}
.legal-footer--dark {
  color: rgba(255, 255, 255, .82);
  text-shadow: 0 1px 2px rgba(0, 0, 0, .22);
}
.legal-footer--dark .legal-brand strong,
.legal-footer--dark .legal-links a:hover {
  color: #fff;
}
.legal-footer--compact {
  gap: 4px;
  font-size: 11px;
}
.legal-footer--compact .legal-brand strong {
  font-size: 12px;
}
@media (max-width: 640px) {
  .legal-brand,
  .legal-links,
  .legal-meta {
    gap: 4px 10px;
  }
}
</style>
