package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Viewmodel.UsuarioViewModel
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    navController: NavController? = null,
    viewModel: WasteViewModel,
    usuarioViewModel: UsuarioViewModel,
    sessionManager: SessionManager
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // VERIFICAR SI ACEPTÓ CÁMARA EN REGISTRO
    val aceptoCamarEnRegistro by remember {
        mutableStateOf(sessionManager.getAceptoCamara())
    }

    // Estados para cámara
    var hasCameraPermission by remember { mutableStateOf(false) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var showCamera by remember { mutableStateOf(false) }
    var isCapturing by remember { mutableStateOf(false) }

    // Executor para cámara
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    // Launcher para permisos (por si perdió el permiso)
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (granted) {
            showCamera = true
        }
    }

    // Verificar permiso al iniciar
    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        hasCameraPermission = hasPermission
        if (hasPermission && aceptoCamarEnRegistro) {
            showCamera = true
        }
    }

    // Vista de cámara en tiempo real
    @Composable
    fun CameraPreview() {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    imageCapture = ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build()

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }

    // Función para tomar foto
    fun takePhoto() {
        val imageCapture = imageCapture ?: return

        isCapturing = true

        // Crear archivo para la foto
        val photoFile = File(
            context.cacheDir,
            "scan_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    isCapturing = false

                    // Obtener URI de la foto
                    val photoUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        photoFile
                    )

                    // Procesar puntos
                    val usuarioId = sessionManager.getUserId()
                    val puntosAgregar = 5

                    if (usuarioId > 0L) {
                        usuarioViewModel.agregarPuntosApi(
                            usuarioId,
                            puntosAgregar,
                            "Escaneo de material reciclable"
                        )
                    }

                    // Navegar a pantalla de éxito
                    navController?.navigate("success_page")
                }

                override fun onError(exception: ImageCaptureException) {
                    isCapturing = false
                    exception.printStackTrace()
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Escanear Materiales",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF81C784)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Toma foto de tus materiales reciclables",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            // Mostrar cámara si tiene permiso
            if (showCamera && hasCameraPermission && aceptoCamarEnRegistro) {
                CameraPreview()
            } else {
                // Placeholder si no hay cámara
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Black.copy(alpha = 0.3F))
                        .border(2.dp, Color.White, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cámara",
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                        Text(
                            if (!aceptoCamarEnRegistro) "No otorgó permiso de cámara"
                            else if (!hasCameraPermission) "Permiso de cámara requerido"
                            else "Preparando cámara...",
                            color = Color.White,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

            Text(
                "Consejos para una buena foto:\n• Buena iluminación\n• Enfoca bien el material\n• Muestra todo el material reciclable",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            // Botón de tomar foto
            Button(
                onClick = {
                    // 1. VERIFICAR SI ACEPTÓ EN REGISTRO
                    if (!aceptoCamarEnRegistro) {
                        // Podrías navegar a perfil para actualizar permisos
                        return@Button
                    }

                    // 2. VERIFICAR PERMISO REAL
                    if (!hasCameraPermission) {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    } else if (!showCamera) {
                        showCamera = true
                    } else {
                        takePhoto()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                ),
                enabled = !isCapturing && aceptoCamarEnRegistro
            ) {
                if (isCapturing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color(0xFF4CAF50)
                    )
                } else {
                    Text(
                        if (!aceptoCamarEnRegistro) "Permiso no otorgado en registro"
                        else if (!hasCameraPermission) "Otorgar permiso de cámara"
                        else if (!showCamera) "Iniciar cámara"
                        else "Tomar Foto",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            OutlinedButton(
                onClick = { navController?.navigate("home_page") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Inicio", color = Color.White)
            }
        }
    }

    // Limpiar recursos al desmontar
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}