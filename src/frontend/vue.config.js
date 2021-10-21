// vue.config.js

module.exports = {
    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'https://springvueconference.herokuapp.com',
                ws: true,
                changeOrigin: true
            }
        }
    }
};