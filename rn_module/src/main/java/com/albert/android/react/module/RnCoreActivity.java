package com.albert.android.react.module;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import java.io.File;
import java.io.IOException;

/**
 * <pre>
 *      Copyright    : Copyright (c) 2019.
 *      Author       : jiaoya.
 *      Created Time : 2019-10-09.
 *      Desc         :
 * </pre>
 */
public class RnCoreActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    private ReactRootView mReactRootView;
    // 用于管理react中的instance 包括生命周期，管理ReactPackage，管理ReactRootView等
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        try {
            mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(getApplication())
                    .setCurrentActivity(this)
                    //.setBundleAssetName("index")  // 要从应用程序的原始资产加载的JS捆绑包文件的名称 index.js文件。(注意在 0.49 版本之前是 index.android.js 文件)
                    .setJSBundleFile(FileUtils.getBundleJsFile(this))// 要从文件系统加载的JS捆绑包文件的路径 例："assets://index.android.js" or "/sdcard/main.jsbundle"
                    .addPackage(new MainReactPackage())
                    .addPackage(new TestReactPackage())
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED) //设置主机的初始生命周期状态
                    .build();


            //  注意参数moduleName对应，这里的MyReactNativeApp必须对应“index.js”中的 “AppRegistry.registerComponent()”的第一个参数，rnApp的名字
            mReactRootView.startReactApplication(mReactInstanceManager, "RnDevelop", null);

            setContentView(mReactRootView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // startReactApplication   调度由JS应用程序从给定JS模块(@{param moduleName})中呈现的react组件的呈现，并将其附加到该管理器的JS上下文中。额外的参数可以用来传递react组件的初始属性

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != mReactInstanceManager) {
            mReactInstanceManager.onNewIntent(intent);
        }
    }

    // 后退按钮事件传递给 React Native
    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }


//    protected ReactInstanceManager createReactInstanceManager() {
//        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
//                .setApplication(mApplication)//设置关联的Application对象
//                .setCurrentActivity(this)
//                .setJSMainModulePath(getJSMainModuleName())  // 设置主MoudleName 其实就是index.android,打包程序服务器上应用程序主模块的路径/在开发期间重新加载JS时使用。所有路径都相对于打包程序从中提供文件的根文件夹。示例： "index.android"或 "subdirectory/index.android"
//                .setBundleAssetName() // 要从应用程序的原始资产加载的JS捆绑包文件的名称。例："index.android.js"
//                .setUseDeveloperSupport(BuildConfig.DEBUG)//设置是否debug，支持reload Js
//                .setRedBoxHandler(getRedBoxHandler())// 红盒的回调,用于DevSupportManagerImpl在开发和处理Redbox中的信息期间拦截所有Redbox的接口。该实现应由ReactInstanceManager中的setRedBoxHandler传递。
//                .setJavaScriptExecutorFactory(getJavaScriptExecutorFactory())//js执行的工厂类，JavaScriptExecutor实现
//                .setUIImplementationProvider(getUIImplementationProvider())//官方建议不使用自定义的UI实现
//                .setBridgeIdleDebugListener() // 用于接收网桥空闲/繁忙事件的通知的接口。不应影响应用程序逻辑，而应仅用于调试/监视/测试目的
//                .setDefaultHardwareBackBtnHandler(this) //设置invokeDefaultOnBackPressed()回调
//                .setJSBundleLoader() // 设置JS环境时使用的Bundle loader。这取代了之前调用setJSBundleFile和setBundleAssetName。例：JSBundleLoader.createFileLoader(application, bundleFile)
//                .setInitialLifecycleState(LifecycleState.BEFORE_CREATE)//设置创建时机,设置主机的初始生命周期状态。例如，如果主机已在创建时恢复，则在收到onPause调用之前，我们不会期望onResume调用
//                .setNativeModuleCallExceptionHandler() // 为所有本机模块调用设置异常处理程序。如果未设置，DevSupportManager则将使用默认值，该默认值 将在开发人员模式下显示一个红框，并在生产模式下重新抛出（崩溃）该应用程序。
//                .setLazyNativeModulesEnabled()   // 是否启用模块的懒加载
//                .setLazyViewManagersEnabled()   // 是否启用视图的懒加载
//                .setDelayViewManagerClassLoadsEnabled() // 是否延时加载ViewManager
//                .setDevBundleDownloadListener();  // 包加载监听器，开发时可使用
//
//
//        for (ReactPackage reactPackage : getPackages()) {
//            builder.addPackage(reactPackage); //添加 ReactPackage
//        }
//
//        String jsBundleFile = getJSBundleFile();
//        if (jsBundleFile != null) {
//            builder.setJSBundleFile(jsBundleFile);  // 要从文件系统加载的JS捆绑包文件的路径。例："assets://index.android.js" or "/sdcard/main.jsbundle"
//        } else {
//            builder.setBundleAssetName(Assertions.assertNotNull(getBundleAssetName()));
//        }
//        return builder.build();
//    }


    protected String getJSBundleFile() {
        String jsBundleFile = getFilesDir().getAbsolutePath() + "/index.android.bundle";
        File file = new File(jsBundleFile);
        return file != null && file.exists() ? jsBundleFile : null;
    }
}
