package com.teethen.sdk.xutil;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.callback.WebServiceCallBack;
import com.teethen.sdk.encryption.AESUtil;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xingq on 2017/12/18.
 */

public class WebServiceUtil {

    private static String TAG = "WebServiceUtil";

    public static String GetWebServiceUrl(String serviceName) {
        return XConstant.SERVER_URL + serviceName + "?wsdl";
    }

    private static final int TIME_OUT = 30000; //超时时间

    // 含有3个线程的线程池
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    /**
     * 调用WebService接口，回调返回JSON/XML格式数据
     * @param url EndPoint
     * @param namespace
     * @param method
     * @param params
     * @param withSoapAction
     * @param needDecryptData
     * @param dataFormat 数据格式 Constant.FORMAT_STR / Constant.FORMAT_OBJ
     * @param soapVersion SOAP协议版本 SoapEnvelope.VER11/SoapEnvelope.VER12
     * @param webServiceCallBack
     */
    public static void call(String url, final String namespace, final String method,
                                      HashMap<String, Object> params,
                                      final boolean withSoapAction,
                                      final boolean needDecryptData,
                                      final int dataFormat,
                                      int soapVersion,
                                      final WebServiceCallBack webServiceCallBack) {

        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE transport = new HttpTransportSE(url, TIME_OUT);

        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(namespace, method);

        // SoapObject添加参数
        if (params != null) {
            for (Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }

        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号(SoapEnvelope.VER11)
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(soapVersion);

        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.dotNet = false;
        transport.debug = false;
        soapEnvelope.bodyOut = soapObject;

        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                webServiceCallBack.callBack(msg.obj); // 将返回值回调到callBack的参数中
            }
        };

        // 开启线程去访问WebService
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String soapAction = TextUtils.isEmpty(namespace) ? null : namespace + method;
                if (dataFormat == XConstant.FORMAT_STR) {
                    String resultStr = null;
                    try {
                        //System.setProperty("http.keepAlive", "false");//解决EOFException
                        soapAction = withSoapAction ? soapAction : null;
                        transport.call(soapAction, soapEnvelope);
                        if (soapEnvelope.getResponse() != null) {
                            resultStr = soapEnvelope.getResponse().toString();
                            if (needDecryptData) {
                                resultStr = AESUtil.decrypt(resultStr);
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        e.printStackTrace();
                        //Log.e(TAG, e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } finally {
                        mHandler.sendMessage(mHandler.obtainMessage(0, resultStr)); // 将获取的消息利用Handler发送到主线程
                    }
                } else {
                    SoapObject resultObject = null;
                    try {
                        //System.setProperty("http.keepAlive", "false");//解决EOFException
                        soapAction = withSoapAction ? soapAction : null;
                        transport.call(soapAction, soapEnvelope);
                        if (soapEnvelope.getResponse() != null) {
                            resultObject = (SoapObject) soapEnvelope.bodyIn; // 获取服务器响应返回的SoapObject
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } finally {
                        mHandler.sendMessage(mHandler.obtainMessage(0, resultObject)); // 将获取的消息利用Handler发送到主线程
                    }
                }
            }
        });
    }

    /**
     * 调用WebService接口，回调返回数据
     * @param params
     * @param method
     * @param dataFormat 数据格式 Constant.FORMAT_STR / Constant.FORMAT_OBJ
     * @param withSoapAction
     * @param webServiceCallBack
     */
    public static void call(HashMap<String, Object> params, final String method, final int dataFormat, final boolean withSoapAction, final WebServiceCallBack webServiceCallBack) {

        final HttpTransportSE transport = new HttpTransportSE(XConstant.WEBSERVICE_URL, TIME_OUT);
        SoapObject soapObject = new SoapObject(XConstant.WEBSERVICE_NAMESPACE, method);

        if (params != null) {
            for (Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }

        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(XConstant.SOAP_VERSION11);

        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.dotNet = false;
        transport.debug = false;
        soapEnvelope.bodyOut = soapObject;

        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                webServiceCallBack.callBack(msg.obj); // 将返回值回调到callBack的参数中
            }
        };

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String soapAction = XConstant.WEBSERVICE_NAMESPACE + method;
                if (dataFormat == XConstant.FORMAT_STR) {
                    String resultStr = null;
                    try {
                        //System.setProperty("http.keepAlive", "false");
                        soapAction = withSoapAction ? soapAction : null;
                        transport.call(soapAction, soapEnvelope);
                        if (soapEnvelope.getResponse() != null) {
                            resultStr = soapEnvelope.getResponse().toString();
                            resultStr = AESUtil.decrypt(resultStr);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } finally {
                        mHandler.sendMessage(mHandler.obtainMessage(0, resultStr));
                    }
                } else {
                    SoapObject resultObject = null;
                    try {
                        //System.setProperty("http.keepAlive", "false");
                        soapAction = withSoapAction ? soapAction : null;
                        transport.call(soapAction, soapEnvelope);
                        if (soapEnvelope.getResponse() != null) {
                            resultObject = (SoapObject) soapEnvelope.bodyIn;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } finally {
                        mHandler.sendMessage(mHandler.obtainMessage(0, resultObject));
                    }
                }
            }
        });
    }
}
