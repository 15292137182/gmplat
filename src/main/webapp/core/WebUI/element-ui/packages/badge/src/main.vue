<template>
  <div class="el-badge">
    <slot></slot>
    <transition name="el-zoom-in-center">
      <sup
        v-show="!hidden && ( data || isDot )"
        v-text="data"
        class="el-badge__content"
        :class="{ 'is-fixed': $slots.default, 'is-dot': isDot }">
      </sup>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'ElBadge',

  props: {
    value: {},
    max: Number,
    isDot: Boolean,
    hidden: Boolean
  },

  computed: {
    data() {
      if (this.isDot) return;

      const value = this.value;
      const max = this.max;

      if (typeof value === 'number' && typeof max === 'number') {
        return max < value ? `${max}+` : value;
      }

      return value;
    }
  }
};
</script>
