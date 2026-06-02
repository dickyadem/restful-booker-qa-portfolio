const { defineConfig } = require('cypress')

module.exports = defineConfig({
   experimentalStudio: true,
  e2e: {
    baseUrl: 'https://automationintesting.online',
    viewportWidth: 1280,
    viewportHeight: 720,
    defaultCommandTimeout: 10000,
    video: false,
    excludeSpecPattern: [
      'cypress/e2e/1-getting-started/**',
      'cypress/e2e/2-advanced-examples/**',
    ],
  },
})