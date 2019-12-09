using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class choose : MonoBehaviour
{
    int i;
    public GameObject now;
    public SkinnedMeshRenderer face;
    public SkinnedMeshRenderer body;
    public Material[] faces;
    public Material[] bodys;
    void Start()
    {
        i = 0;
        data.face = faces[0];
        data.body = bodys[0];
    }
    public void left()
    {
        if (i > 0)
        {
            i -= 1;
            face.material = faces[i];
            body.material = bodys[i];
            store(i);
        }
    }

    public void right()
    {
        if (i < faces.Length - 1)
        {
            i += 1;
            face.material = faces[i];
            body.material = bodys[i];
            store(i);
        }
    }

    void store(int i)
    {
        data.face = faces[i];
        data.body = bodys[i];
    }
}
