#ifndef AMBEDECODERHEADER_H
#define AMBEDECODERHEADER_H
#ifdef AMBEDECODER_EXPORTS
#define AMBEDECODER_API __declspec(dllexport)
#else
#define AMBEDECODER_API __declspec(dllimport)
#endif

/*
功能：将信道译码后的语音数据文件解码成WAV格式的语音文件，并将上下行语音文件合路
参数：
输入：DownFilePath为下行语音信道译码后的数据文件路径,为空时表示没有下行语音数据
输入：UpFilePath  为上行语音信道译码后的数据文件路径,为空时表示没有上行语音数据
输出：outFilePath WAV语音输出文件路径，不为能为空且文件名必须以.WAV为后缀
返回值：
0 ：成功；
-1：下行文件打开失败；
-2：上行文件打开失败；
-3：创建输出文件失败；
-4：上下行输出文件路径均为空；
-5: 输出文件路径为空；
-6：语音译码失败；
-7: 临时存储文件创建失败;
-8: 文件合路失败
*/
AMBEDECODER_API int AmbeDecoder(const char* DownFilePath, const char* UpFilePath, const char* outFilePath);

#endif