#ifndef AMBEDECODERHEADER_H
#define AMBEDECODERHEADER_H
#ifdef AMBEDECODER_EXPORTS
#define AMBEDECODER_API __declspec(dllexport)
#else
#define AMBEDECODER_API __declspec(dllimport)
#endif

/*
���ܣ����ŵ����������������ļ������WAV��ʽ�������ļ������������������ļ���·
������
���룺DownFilePathΪ���������ŵ������������ļ�·��,Ϊ��ʱ��ʾû��������������
���룺UpFilePath  Ϊ���������ŵ������������ļ�·��,Ϊ��ʱ��ʾû��������������
�����outFilePath WAV��������ļ�·������Ϊ��Ϊ�����ļ���������.WAVΪ��׺
����ֵ��
0 ���ɹ���
-1�������ļ���ʧ�ܣ�
-2�������ļ���ʧ�ܣ�
-3����������ļ�ʧ�ܣ�
-4������������ļ�·����Ϊ�գ�
-5: ����ļ�·��Ϊ�գ�
-6����������ʧ�ܣ�
-7: ��ʱ�洢�ļ�����ʧ��;
-8: �ļ���·ʧ��
*/
AMBEDECODER_API int AmbeDecoder(const char* DownFilePath, const char* UpFilePath, const char* outFilePath);

#endif