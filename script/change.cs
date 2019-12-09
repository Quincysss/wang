using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class change : MonoBehaviour
{
    public SkinnedMeshRenderer face;
    public SkinnedMeshRenderer body;
    void Start()
    {
        face.material = data.face;
        body.material = data.body;
    }
}
